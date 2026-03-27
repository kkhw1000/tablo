package org.example.tablo.store.dto;

public record StoreSummaryResponse(
        Long id,
        String name,
        String phoneNumber,
        String city,
        String district,
        String description
) {
}
