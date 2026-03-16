package com.nissan.template.security.controller;

import com.nissan.template.common.response.ApiResponse;
import com.nissan.template.security.dto.AuthResponse;
import com.nissan.template.security.dto.LoginRequest;
import com.nissan.template.security.dto.RegisterRequest;
import com.nissan.template.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ApiResponse.success(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ApiResponse.success(authService.register(registerRequest));
    }
}
