package org.example.tablo.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tablo.auth.domain.AuthException;
import org.example.tablo.auth.dto.AuthResponse;
import org.example.tablo.auth.dto.LoginRequest;
import org.example.tablo.auth.dto.SignUpRequest;
import org.example.tablo.common.exception.ErrorCode;
import org.example.tablo.user.domain.User;
import org.example.tablo.user.domain.UserDomainException;
import org.example.tablo.user.domain.UserRole;
import org.example.tablo.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        log.info("회원가입을 시도합니다. email={}", request.email());
        validateDuplicateUser(request.email(), request.phoneNumber());

        User user = User.create(
                request.email(),
                request.name(),
                request.phoneNumber(),
                passwordEncoder.encode(request.password()),
                UserRole.CUSTOMER
        );

        User savedUser = userRepository.save(user);
        String accessToken = jwtTokenProvider.createAccessToken(savedUser);
        log.info("회원가입이 완료되었습니다. userId={}, email={}", savedUser.getId(), savedUser.getEmail());

        return toAuthResponse(savedUser, accessToken);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        log.info("로그인을 시도합니다. email={}", request.email());
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException(ErrorCode.INVALID_LOGIN_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            log.warn("비밀번호가 일치하지 않아 로그인에 실패했습니다. email={}", request.email());
            throw new AuthException(ErrorCode.INVALID_LOGIN_CREDENTIALS);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        log.info("로그인이 완료되었습니다. userId={}, email={}", user.getId(), user.getEmail());
        return toAuthResponse(user, accessToken);
    }

    private void validateDuplicateUser(String email, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            log.warn("이미 가입된 이메일이라 회원가입을 진행할 수 없습니다. email={}", email);
            throw new UserDomainException(ErrorCode.DUPLICATE_USER_EMAIL);
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            log.warn("이미 가입된 전화번호라 회원가입을 진행할 수 없습니다. phoneNumber={}", phoneNumber);
            throw new UserDomainException(ErrorCode.DUPLICATE_USER_PHONE_NUMBER);
        }
    }

    private AuthResponse toAuthResponse(User user, String accessToken) {
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                accessToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }
}
