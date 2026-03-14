package org.example.tablo.user.controller;

import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.common.response.ApiResponse;
import org.example.tablo.common.response.SuccessCode;
import org.example.tablo.user.dto.UserMeResponse;
import org.example.tablo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserMeResponse> getMe(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ApiResponse.of(SuccessCode.USER_ME_SUCCESS, userService.getMe(authenticatedUser));
    }
}
