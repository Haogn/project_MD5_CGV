package com.ra.controller;

import com.ra.dto.request.ChangePassword;
import com.ra.dto.request.UserLogin;
import com.ra.dto.request.UserRegister;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService ;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "") String name ,
                                        @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return new ResponseEntity<>(userService.findAllUser(name, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @PatchMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatusUser(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(userService.changeStatusUser(id), HttpStatus.OK);
    }



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

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePassword changePassword) throws CustomException {
        String successMessage = null ;
        if (userService.changePassword(id, changePassword)) {
            successMessage = "Password changed successfully";
        }
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
