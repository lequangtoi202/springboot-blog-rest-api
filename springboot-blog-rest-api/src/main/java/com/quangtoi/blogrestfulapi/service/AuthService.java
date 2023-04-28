package com.quangtoi.blogrestfulapi.service;

import com.quangtoi.blogrestfulapi.dto.LoginDto;
import com.quangtoi.blogrestfulapi.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
