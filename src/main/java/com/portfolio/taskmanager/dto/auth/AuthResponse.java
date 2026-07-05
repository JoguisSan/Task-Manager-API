package com.portfolio.taskmanager.dto.auth;

public record AuthResponse(
        String token,
        String tokenType,
        String username
) {
    public static AuthResponse of(String token, String username) {
        return new AuthResponse(token, "Bearer", username);
    }
}
