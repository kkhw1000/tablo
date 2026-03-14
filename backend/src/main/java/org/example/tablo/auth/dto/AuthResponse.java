package org.example.tablo.auth.dto;

public record AuthResponse(
        Long userId,
        String email,
        String name,
        String role,
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
