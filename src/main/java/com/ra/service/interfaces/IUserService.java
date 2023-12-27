package com.ra.service.interfaces;

import com.ra.dto.request.ChangePassword;
import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.JwtResponse;
import com.ra.dto.response.UserResponse;
import com.ra.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void register(UserRegister userRegister) throws CustomException;

    JwtResponse login (UserLogin userLogin, HttpSession session ) throws CustomException;

    Boolean changePassword(Long id, ChangePassword changePassword) throws CustomException;

    Page<UserResponse> findAllUser(String name, Pageable pageable) ;
    UserResponse findById(Long id) throws CustomException;
    Boolean changeStatusUser(Long id) throws CustomException;
}
