package org.example.tablo.reservation.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReservationRequest(
        @NotNull(message = "storeId is required")
        Long storeId,

        @Min(value = 1, message = "partySize must be at least 1")
        int partySize,

        @NotNull(message = "reservedAt is required")
        @Future(message = "reservedAt must be in the future")
        LocalDateTime reservedAt,

        @Size(max = 500, message = "requestNote must be 500 characters or fewer")
        String requestNote
) {
}
