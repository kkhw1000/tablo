package org.example.tablo.reservation.domain;

import java.time.LocalDateTime;

import org.example.tablo.common.entity.BaseTimeEntity;
import org.example.tablo.common.exception.ErrorCode;
import org.example.tablo.store.domain.Store;
import org.example.tablo.store.domain.StoreTable;
import org.example.tablo.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "reservations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private StoreTable table;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;

    @Column(nullable = false)
    private int partySize;

    @Column(nullable = false)
    private LocalDateTime reservedAt;

    @Column(length = 500)
    private String requestNote;

    private LocalDateTime canceledAt;

    private LocalDateTime seatedAt;

    private Reservation(
            User user,
            Store store,
            StoreTable table,
            int partySize,
            LocalDateTime reservedAt,
            String requestNote
    ) {
        this.user = user;
        this.store = store;
        this.table = table;
        this.status = ReservationStatus.PENDING;
        this.partySize = partySize;
        this.reservedAt = reservedAt;
        this.requestNote = requestNote;
    }

    public static Reservation create(
            User user,
            Store store,
            StoreTable table,
            int partySize,
            LocalDateTime reservedAt,
            String requestNote
    ) {
        return new Reservation(user, store, table, partySize, reservedAt, requestNote);
    }

    public void confirm() {
        if (status != ReservationStatus.PENDING) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void assignTable(StoreTable table) {
        this.table = table;
    }

    public void seat(LocalDateTime seatedAt) {
        if (status != ReservationStatus.CONFIRMED) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
        this.status = ReservationStatus.SEATED;
        this.seatedAt = seatedAt;
    }

    public void complete() {
        if (status != ReservationStatus.SEATED) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
        this.status = ReservationStatus.COMPLETED;
    }

    public void cancel(LocalDateTime canceledAt) {
        if (status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELED) {
            throw new ReservationDomainException(ErrorCode.RESERVATION_ALREADY_CANCELED);
        }
        this.status = ReservationStatus.CANCELED;
        this.canceledAt = canceledAt;
    }

    public void markNoShow() {
        if (status != ReservationStatus.CONFIRMED) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
        this.status = ReservationStatus.NO_SHOW;
    }
}
