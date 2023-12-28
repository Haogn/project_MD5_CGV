package com.ra.service.impl;

import com.ra.dto.request.ChangePassword;
import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.JwtResponse;
import com.ra.dto.response.UserResponse;
import com.ra.entity.*;
import com.ra.exception.CustomException;
import com.ra.mapper.UserMapper;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrincipal;
import com.ra.service.interfaces.IRoleService;
import com.ra.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserMapper userMapper;


    @Override
    public void register(UserRegister userRegister) throws CustomException {
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(userRegister.getEmail())) {
            throw new CustomException("Email exists");
        }

        Set<Roles> roles = new HashSet<>();
        // Nếu không có quyền được truyền lên, mặc định là role user
        if (userRegister.getRoles() == null || userRegister.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            // Xác định quyền dựa trên danh sách quyền được truyền lên
            userRegister.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "user":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        break;
                    default:
                        try {
                            throw new CustomException("role not found");
                        } catch (CustomException e) {
                            throw new RuntimeException(e);
                        }
                }
            });
        }

        MemberLevelName memberLevers = null;
        // neu khong co hang dc them vao , mac dinh la hang bac
        if (userRegister.getMemberLever() == null || userRegister.getMemberLever().isEmpty()) {
            memberLevers = MemberLevelName.BRONZE;
        }

        userRepository.save(Users.builder()
                .email(userRegister.getEmail())
                .userName(userRegister.getUserName())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .birthday(userRegister.getBirthday())
                .status(true)
                .roles(roles)
                .scorePoints(0)
                .memberLevers(memberLevers)
                .build());
    }

    @Override
    public JwtResponse login(UserLogin userLogin, HttpSession session) throws CustomException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword()));
        } catch (AuthenticationException e) {
            Integer errorLogin = (Integer) session.getAttribute("errorLogin");
            if (errorLogin == null) {
                session.setAttribute("errorLogin", 1);
            } else {
                // Không phải lần đầu tiên nhập sai
                if (errorLogin == 3) {
                    // Khi số lần nhập sai đạt mức 3, khoá tài khoản
                    Users users = userRepository.findByUserName(userLogin.getUserName()).orElseThrow(() -> new CustomException("user not found"));
                    users.setStatus(false);
                    userRepository.save(users);
                    throw new CustomException("your account is blocked");
                } else {
                    // Tăng số lần nhập sai
                    session.setAttribute("count", errorLogin + 1);
                }
            }
            throw new CustomException("Username or Password is incorrect");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();


        if (!userPrincipal.getStatus()) {
            throw new CustomException("your account is blocked");
        }

        return JwtResponse.builder()
                .userName(userLogin.getUserName())
                .email(userPrincipal.getEmail())
                .token(jwtProvider.generateToken(userPrincipal))
                .status(userPrincipal.getStatus())
                .birthday(userPrincipal.getBirthday())
                .roles(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .memberLever(userPrincipal.getMembershipLevel())
                .build();
    }

    @Override
    public Boolean changePassword(Long id, ChangePassword changePassword) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));

        if (passwordEncoder.matches(changePassword.getOldPassword(), users.getPassword())) {
            if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
                String newPass = passwordEncoder.encode(changePassword.getNewPassword());
                users.setPassword(newPass);
                userRepository.save(users);
                return true;
            } else {
                throw new CustomException("NewPassword does not match the elephant ConfirmPassword");
            }
        } else {
            throw new CustomException("OldPassword do not match");
        }
    }

    @Override
    public Page<UserResponse> findAllUser(String name, Pageable pageable) {
        Page<Users> page;
        if (name.isEmpty()) {
            page = userRepository.findAll(pageable);
        } else {
            page = userRepository.findAllByUserNameContainingIgnoreCase(name, pageable);
        }
        return page.map(item -> userMapper.toUserResponse(item));
    }

    @Override
    public UserResponse findById(Long id) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
        return userMapper.toUserResponse(users);
    }

    @Override
    public Boolean changeStatusUser(Long id) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
        if (users.getRoles().equals(RoleName.ROLE_ADMIN)) {
            throw new CustomException("Admin cannot lock account");
        }
        users.setStatus(!users.getStatus());
        userRepository.save(users);
        return true;
    }
}
