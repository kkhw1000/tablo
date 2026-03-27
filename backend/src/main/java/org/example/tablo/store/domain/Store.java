package org.example.tablo.store.domain;

import org.example.tablo.common.entity.BaseTimeEntity;
import org.example.tablo.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 150)
    private String name;

    @Embedded
    private StoreContact contact;

    @Embedded
    private StoreAddress address;

    @Column(length = 1000)
    private String description;

    private Store(
            User owner,
            String name,
            StoreContact contact,
            StoreAddress address,
            String description
    ) {
        this.owner = owner;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.description = description;
    }

    public static Store create(
            User owner,
            String name,
            StoreContact contact,
            StoreAddress address,
            String description
    ) {
        return new Store(owner, name, contact, address, description);
    }
}
