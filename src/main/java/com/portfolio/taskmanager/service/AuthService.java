package com.portfolio.taskmanager.service;

import com.portfolio.taskmanager.dto.auth.AuthResponse;
import com.portfolio.taskmanager.dto.auth.LoginRequest;
import com.portfolio.taskmanager.dto.auth.RegisterRequest;
import com.portfolio.taskmanager.entity.Role;
import com.portfolio.taskmanager.entity.User;
import com.portfolio.taskmanager.exception.DuplicateResourceException;
import com.portfolio.taskmanager.repository.UserRepository;
import com.portfolio.taskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username já está em uso: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email já está em uso: " + request.email());
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponse.of(token, user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado após autenticação"));

        String token = jwtService.generateToken(user);
        return AuthResponse.of(token, user.getUsername());
    }
}
