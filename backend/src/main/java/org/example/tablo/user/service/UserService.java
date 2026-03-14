package org.example.tablo.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.user.dto.UserMeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Transactional(readOnly = true)
    public UserMeResponse getMe(AuthenticatedUser authenticatedUser) {
        log.info("현재 로그인한 사용자 정보를 조회합니다. userId={}, email={}", authenticatedUser.id(), authenticatedUser.email());
        return new UserMeResponse(
                authenticatedUser.id(),
                authenticatedUser.email(),
                authenticatedUser.name(),
                authenticatedUser.phoneNumber(),
                authenticatedUser.role()
        );
    }
}
