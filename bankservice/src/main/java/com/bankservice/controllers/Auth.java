package com.bankservice.controllers;

import com.bankservice.dto.LoginRequest;
import com.bankservice.dto.LoginResponse;
import com.bankservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Auth {

    private final AuthService userService;

    @GetMapping("/test-live")
    public String testLiveStatus(){
        return "App is running....!";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin (@RequestBody LoginRequest request){
        LoginResponse apiResponse = userService.authenticate(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}