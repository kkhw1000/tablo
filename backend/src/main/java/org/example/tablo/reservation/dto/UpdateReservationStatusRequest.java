package org.example.tablo.reservation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateReservationStatusRequest(
        @NotBlank(message = "status is required")
        String status
) {
}
