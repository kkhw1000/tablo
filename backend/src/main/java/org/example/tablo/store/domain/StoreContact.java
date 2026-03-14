package org.example.tablo.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreContact {

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    private StoreContact(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static StoreContact of(String phoneNumber) {
        return new StoreContact(phoneNumber);
    }
}
