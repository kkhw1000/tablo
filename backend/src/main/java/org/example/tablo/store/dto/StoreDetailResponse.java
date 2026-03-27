package org.example.tablo.store.dto;

public record StoreDetailResponse(
        Long id,
        Long ownerId,
        String name,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String district,
        String description
) {
}
