package org.example.tablo.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.tablo.auth.dto.AuthResponse;
import org.example.tablo.auth.dto.LoginRequest;
import org.example.tablo.auth.dto.SignUpRequest;
import org.example.tablo.auth.service.AuthService;
import org.example.tablo.common.response.ApiResponse;
import org.example.tablo.common.response.SuccessCode;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ApiResponse.of(SuccessCode.USER_SIGN_UP_SUCCESS, authService.signUp(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.of(SuccessCode.USER_LOGIN_SUCCESS, authService.login(request));
    }
}
