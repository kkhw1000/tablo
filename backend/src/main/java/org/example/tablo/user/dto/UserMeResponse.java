package org.example.tablo.user.dto;

public record UserMeResponse(
        Long id,
        String email,
        String name,
        String phoneNumber,
        String role
) {
}
