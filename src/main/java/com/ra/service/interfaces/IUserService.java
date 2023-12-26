package com.ra.service.interfaces;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.dto.response.JwtResponse;
import com.ra.dto.response.UserResponse;
import com.ra.exception.CustomException;
import jakarta.servlet.http.HttpSession;

public interface IUserService {
    void register(UserRegister userRegister) throws CustomException;

    JwtResponse login (UserLogin userLogin, HttpSession session ) throws CustomException;
}
