package org.example.tablo.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.common.exception.ErrorCode;
import org.example.tablo.reservation.domain.Reservation;
import org.example.tablo.reservation.domain.ReservationDomainException;
import org.example.tablo.reservation.domain.ReservationStatus;
import org.example.tablo.reservation.dto.CreateReservationRequest;
import org.example.tablo.reservation.dto.ReservationDetailResponse;
import org.example.tablo.reservation.dto.ReservationStoreResponse;
import org.example.tablo.reservation.dto.ReservationSummaryResponse;
import org.example.tablo.reservation.dto.ReservationTableResponse;
import org.example.tablo.reservation.dto.UpdateReservationStatusRequest;
import org.example.tablo.reservation.repository.ReservationRepository;
import org.example.tablo.store.domain.Store;
import org.example.tablo.store.domain.StoreDomainException;
import org.example.tablo.store.repository.StoreRepository;
import org.example.tablo.user.domain.User;
import org.example.tablo.user.domain.UserRole;
import org.example.tablo.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationDetailResponse createReservation(AuthenticatedUser authenticatedUser, CreateReservationRequest request) {
        if (request.partySize() < 1) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_PARTY_SIZE);
        }
        if (request.reservedAt().isBefore(LocalDateTime.now())) {
            throw new ReservationDomainException(ErrorCode.RESERVATION_TIME_IN_PAST);
        }

        User user = getUser(authenticatedUser.id());
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreDomainException(ErrorCode.STORE_NOT_FOUND));

        Reservation reservation = Reservation.create(
                user,
                store,
                null,
                request.partySize(),
                request.reservedAt(),
                request.requestNote()
        );

        return toDetailResponse(reservationRepository.save(reservation));
    }

    @Transactional(readOnly = true)
    public List<ReservationSummaryResponse> getMyReservations(AuthenticatedUser authenticatedUser) {
        return reservationRepository.findAllByUserIdOrderByReservedAtDesc(authenticatedUser.id())
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservationDetailResponse getMyReservation(AuthenticatedUser authenticatedUser, Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, authenticatedUser.id())
                .orElseThrow(() -> new ReservationDomainException(ErrorCode.RESERVATION_NOT_FOUND));
        return toDetailResponse(reservation);
    }

    @Transactional
    public ReservationDetailResponse cancelMyReservation(AuthenticatedUser authenticatedUser, Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, authenticatedUser.id())
                .orElseThrow(() -> new ReservationDomainException(ErrorCode.RESERVATION_NOT_FOUND));
        reservation.cancel(LocalDateTime.now());
        return toDetailResponse(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationSummaryResponse> getOwnerReservations(
            AuthenticatedUser authenticatedUser,
            Long storeId,
            String status
    ) {
        Store store = getAccessibleStore(authenticatedUser, storeId);

        List<Reservation> reservations = status == null || status.isBlank()
                ? reservationRepository.findAllByStoreIdOrderByReservedAtAsc(store.getId())
                : reservationRepository.findAllByStoreIdAndStatusOrderByReservedAtAsc(store.getId(), parseStatus(status));

        return reservations.stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Transactional
    public ReservationDetailResponse updateOwnerReservationStatus(
            AuthenticatedUser authenticatedUser,
            Long storeId,
            Long reservationId,
            UpdateReservationStatusRequest request
    ) {
        Store store = getAccessibleStore(authenticatedUser, storeId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationDomainException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!reservation.getStore().getId().equals(store.getId())) {
            throw new ReservationDomainException(ErrorCode.RESERVATION_NOT_FOUND);
        }

        ReservationStatus status = parseStatus(request.status());
        if (status == ReservationStatus.CONFIRMED) {
            reservation.confirm();
        } else if (status == ReservationStatus.CANCELED) {
            reservation.cancel(LocalDateTime.now());
        } else {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }

        return toDetailResponse(reservation);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ReservationDomainException(ErrorCode.FORBIDDEN));
    }

    private Store getAccessibleStore(AuthenticatedUser authenticatedUser, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreDomainException(ErrorCode.STORE_NOT_FOUND));

        boolean isAdmin = UserRole.ADMIN.name().equals(authenticatedUser.role());
        boolean isOwner = UserRole.OWNER.name().equals(authenticatedUser.role());
        boolean ownsStore = store.getOwner().getId().equals(authenticatedUser.id());

        if (!(isAdmin || (isOwner && ownsStore))) {
            throw new ReservationDomainException(ErrorCode.FORBIDDEN);
        }
        return store;
    }

    private ReservationStatus parseStatus(String status) {
        try {
            return ReservationStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new ReservationDomainException(ErrorCode.INVALID_RESERVATION_STATUS);
        }
    }

    private ReservationSummaryResponse toSummaryResponse(Reservation reservation) {
        return new ReservationSummaryResponse(
                reservation.getId(),
                reservation.getStatus().name(),
                reservation.getPartySize(),
                reservation.getReservedAt(),
                reservation.getRequestNote(),
                reservation.getCanceledAt(),
                reservation.getCreatedAt(),
                new ReservationStoreResponse(
                        reservation.getStore().getId(),
                        reservation.getStore().getName()
                ),
                toTableResponse(reservation)
        );
    }

    private ReservationDetailResponse toDetailResponse(Reservation reservation) {
        return new ReservationDetailResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getUser().getName(),
                reservation.getUser().getPhoneNumber(),
                reservation.getStatus().name(),
                reservation.getPartySize(),
                reservation.getReservedAt(),
                reservation.getRequestNote(),
                reservation.getCanceledAt(),
                reservation.getSeatedAt(),
                reservation.getCreatedAt(),
                new ReservationStoreResponse(
                        reservation.getStore().getId(),
                        reservation.getStore().getName()
                ),
                toTableResponse(reservation)
        );
    }

    private ReservationTableResponse toTableResponse(Reservation reservation) {
        if (reservation.getTable() == null) {
            return null;
        }
        return new ReservationTableResponse(
                reservation.getTable().getId(),
                reservation.getTable().getName(),
                reservation.getTable().getCapacity()
        );
    }
}
