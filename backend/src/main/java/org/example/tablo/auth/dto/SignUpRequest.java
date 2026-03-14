package org.example.tablo.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Pattern(regexp = "^[0-9\\-+]{8,20}$") String phoneNumber,
        @NotBlank @Size(min = 8, max = 100) String password
) {
}
