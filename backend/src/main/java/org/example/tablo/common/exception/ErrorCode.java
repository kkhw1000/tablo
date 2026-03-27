package org.example.tablo.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "Unexpected server error."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_400_1", "Invalid input value."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403_1", "You do not have permission to access this resource."),
    DUPLICATE_USER_EMAIL(HttpStatus.CONFLICT, "USER_409_1", "Email is already registered."),
    DUPLICATE_USER_PHONE_NUMBER(HttpStatus.CONFLICT, "USER_409_2", "Phone number is already registered."),
    INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "Invalid email or password."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_2", "Invalid or expired token."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_404_1", "Store not found."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVATION_404_1", "Reservation not found."),
    RESERVATION_TIME_IN_PAST(HttpStatus.BAD_REQUEST, "RESERVATION_400_3", "Reservation time must be in the future."),
    INVALID_RESERVATION_PARTY_SIZE(HttpStatus.BAD_REQUEST, "RESERVATION_400_4", "Party size must be at least 1."),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST, "RESERVATION_400_1", "Invalid reservation status transition."),
    RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "RESERVATION_400_2", "Reservation is already canceled or completed.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
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
