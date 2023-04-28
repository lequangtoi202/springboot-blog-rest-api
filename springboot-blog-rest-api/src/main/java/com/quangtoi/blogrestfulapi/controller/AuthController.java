package com.quangtoi.blogrestfulapi.controller;

import com.quangtoi.blogrestfulapi.dto.JwtAuthResponse;
import com.quangtoi.blogrestfulapi.dto.LoginDto;
import com.quangtoi.blogrestfulapi.dto.RegisterDto;
import com.quangtoi.blogrestfulapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setAccessToken(token);

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return ResponseEntity.ok().body(response);
    }
}
