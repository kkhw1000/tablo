package org.example.tablo.common.response;

import org.springframework.http.HttpStatus;

public enum SuccessCode {
    OK(HttpStatus.OK, "COMMON_200", "OK"),
    USER_SIGN_UP_SUCCESS(HttpStatus.CREATED, "AUTH_201_1", "User signed up successfully."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200_1", "User logged in successfully."),
    USER_ME_SUCCESS(HttpStatus.OK, "USER_200_1", "Current user fetched successfully.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
