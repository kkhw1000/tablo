package org.example.tablo.reservation.controller;

import java.util.List;

import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.common.response.ApiResponse;
import org.example.tablo.common.response.SuccessCode;
import org.example.tablo.reservation.dto.CreateReservationRequest;
import org.example.tablo.reservation.dto.ReservationDetailResponse;
import org.example.tablo.reservation.dto.ReservationSummaryResponse;
import org.example.tablo.reservation.dto.UpdateReservationStatusRequest;
import org.example.tablo.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationDetailResponse> createReservation(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CreateReservationRequest request
    ) {
        return ApiResponse.of(
                SuccessCode.RESERVATION_CREATE_SUCCESS,
                reservationService.createReservation(authenticatedUser, request)
        );
    }

    @GetMapping("/api/reservations")
    public ApiResponse<List<ReservationSummaryResponse>> getMyReservations(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ApiResponse.of(
                SuccessCode.MY_RESERVATION_LIST_SUCCESS,
                reservationService.getMyReservations(authenticatedUser)
        );
    }

    @GetMapping("/api/reservations/{reservationId}")
    public ApiResponse<ReservationDetailResponse> getMyReservation(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long reservationId
    ) {
        return ApiResponse.of(
                SuccessCode.MY_RESERVATION_DETAIL_SUCCESS,
                reservationService.getMyReservation(authenticatedUser, reservationId)
        );
    }

    @PatchMapping("/api/reservations/{reservationId}/cancel")
    public ApiResponse<ReservationDetailResponse> cancelMyReservation(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long reservationId
    ) {
        return ApiResponse.of(
                SuccessCode.RESERVATION_CANCEL_SUCCESS,
                reservationService.cancelMyReservation(authenticatedUser, reservationId)
        );
    }

    @GetMapping("/api/owner/stores/{storeId}/reservations")
    public ApiResponse<List<ReservationSummaryResponse>> getOwnerReservations(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long storeId,
            @RequestParam(required = false) String status
    ) {
        return ApiResponse.of(
                SuccessCode.OWNER_RESERVATION_LIST_SUCCESS,
                reservationService.getOwnerReservations(authenticatedUser, storeId, status)
        );
    }

    @PatchMapping("/api/owner/stores/{storeId}/reservations/{reservationId}/status")
    public ApiResponse<ReservationDetailResponse> updateOwnerReservationStatus(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long storeId,
            @PathVariable Long reservationId,
            @Valid @RequestBody UpdateReservationStatusRequest request
    ) {
        return ApiResponse.of(
                SuccessCode.OWNER_RESERVATION_STATUS_UPDATE_SUCCESS,
                reservationService.updateOwnerReservationStatus(authenticatedUser, storeId, reservationId, request)
        );
    }
}
