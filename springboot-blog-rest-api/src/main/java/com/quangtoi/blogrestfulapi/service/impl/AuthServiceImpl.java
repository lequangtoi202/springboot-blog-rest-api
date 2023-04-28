package com.quangtoi.blogrestfulapi.service.impl;

import com.quangtoi.blogrestfulapi.dto.LoginDto;
import com.quangtoi.blogrestfulapi.dto.RegisterDto;
import com.quangtoi.blogrestfulapi.exception.BlogApiException;
import com.quangtoi.blogrestfulapi.model.Role;
import com.quangtoi.blogrestfulapi.model.User;
import com.quangtoi.blogrestfulapi.repository.RoleRepository;
import com.quangtoi.blogrestfulapi.repository.UserRepository;
import com.quangtoi.blogrestfulapi.security.JwtTokenProvider;
import com.quangtoi.blogrestfulapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepo.existsByUsername(registerDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already exist");
        }

        if (userRepo.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exist");
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        roles.add(userRole);
        userRepo.save(user);

        return "User registered successfully";
    }
}
