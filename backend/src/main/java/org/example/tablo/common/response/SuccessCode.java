package org.example.tablo.common.response;

import org.springframework.http.HttpStatus;

public enum SuccessCode {
    OK(HttpStatus.OK, "COMMON_200", "OK"),
    USER_SIGN_UP_SUCCESS(HttpStatus.CREATED, "AUTH_201_1", "User signed up successfully."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200_1", "User logged in successfully."),
    USER_ME_SUCCESS(HttpStatus.OK, "USER_200_1", "Current user fetched successfully."),
    STORE_LIST_SUCCESS(HttpStatus.OK, "STORE_200_1", "Stores fetched successfully."),
    STORE_DETAIL_SUCCESS(HttpStatus.OK, "STORE_200_2", "Store fetched successfully."),
    RESERVATION_CREATE_SUCCESS(HttpStatus.CREATED, "RESERVATION_201_1", "Reservation created successfully."),
    MY_RESERVATION_LIST_SUCCESS(HttpStatus.OK, "RESERVATION_200_1", "My reservations fetched successfully."),
    MY_RESERVATION_DETAIL_SUCCESS(HttpStatus.OK, "RESERVATION_200_2", "Reservation fetched successfully."),
    RESERVATION_CANCEL_SUCCESS(HttpStatus.OK, "RESERVATION_200_3", "Reservation canceled successfully."),
    OWNER_RESERVATION_LIST_SUCCESS(HttpStatus.OK, "RESERVATION_200_4", "Store reservations fetched successfully."),
    OWNER_RESERVATION_STATUS_UPDATE_SUCCESS(HttpStatus.OK, "RESERVATION_200_5", "Reservation status updated successfully.");

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
