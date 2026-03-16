package com.nissan.template.security.service;

import com.nissan.template.domain.User;
import com.nissan.template.exception.BusinessException;
import com.nissan.template.repository.UserRepository;
import com.nissan.template.security.dto.AuthResponse;
import com.nissan.template.security.dto.LoginRequest;
import com.nissan.template.security.dto.RegisterRequest;
import com.nissan.template.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        return AuthResponse.builder()
                .token(jwt)
                .username(userDetails.getUsername())
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);

        return login(new LoginRequest(registerRequest.getUsername(), registerRequest.getPassword()));
    }
}
