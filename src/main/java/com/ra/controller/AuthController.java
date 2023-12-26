package com.ra.controller;

import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService ;


    // todo : register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister) throws CustomException {
        userService.register(userRegister);
        String successMessage = "User created successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    // todo : login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin, HttpSession session) throws CustomException {
        return new ResponseEntity<>(userService.login(userLogin, session), HttpStatus.OK) ;
    }
}
