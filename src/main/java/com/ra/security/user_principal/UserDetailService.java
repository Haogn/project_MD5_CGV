package com.ra.security.user_principal;

import com.ra.entity.Users;
import com.ra.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm người dùng trong cơ sở dữ liệu theo tên người dùng
        Optional<Users> usersOptional = userRepository.findByUserName(username);

        if (usersOptional.isPresent()) {
            // Nếu người dùng tồn tại, sử dụng lớp UserPrincipal để tạo UserDetails
            UserDetails userDetails = UserPrincipal.build(usersOptional.get());
            return userDetails;
        }

        // Nếu không tìm thấy người dùng, ném ngoại lệ UsernameNotFoundException
        throw new UsernameNotFoundException(username + " not found");
    }
}
