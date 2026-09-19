package com.devassist.auth.service;

import com.devassist.auth.dto.LoginRequest;
import com.devassist.auth.dto.LoginResponse;
import com.devassist.auth.dto.RegisterRequest;
import com.devassist.auth.entity.User;
import com.devassist.auth.enums.Role;
import com.devassist.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already taken");
            }

            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);

            return "User registered successfully";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public LoginResponse login(LoginRequest request) {

        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid email or password");
            }

            String token = jwtService.generateToken(user);

            return new LoginResponse(token);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
