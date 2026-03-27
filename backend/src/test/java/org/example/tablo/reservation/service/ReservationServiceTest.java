package org.example.tablo.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.common.exception.DomainException;
import org.example.tablo.common.exception.ErrorCode;
import org.example.tablo.reservation.domain.Reservation;
import org.example.tablo.reservation.domain.ReservationStatus;
import org.example.tablo.reservation.dto.CreateReservationRequest;
import org.example.tablo.reservation.dto.ReservationSummaryResponse;
import org.example.tablo.reservation.dto.UpdateReservationStatusRequest;
import org.example.tablo.reservation.repository.ReservationRepository;
import org.example.tablo.store.domain.Store;
import org.example.tablo.store.domain.StoreAddress;
import org.example.tablo.store.domain.StoreContact;
import org.example.tablo.store.repository.StoreRepository;
import org.example.tablo.user.domain.User;
import org.example.tablo.user.domain.UserRole;
import org.example.tablo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    private User customer;
    private User owner;
    private Store store;
    private AuthenticatedUser customerPrincipal;
    private AuthenticatedUser ownerPrincipal;

    @BeforeEach
    void setUp() {
        customer = User.create("customer@test.com", "Customer", "010-1111-1111", "encoded", UserRole.CUSTOMER);
        ReflectionTestUtils.setField(customer, "id", 1L);

        owner = User.create("owner@test.com", "Owner", "010-2222-2222", "encoded", UserRole.OWNER);
        ReflectionTestUtils.setField(owner, "id", 2L);

        store = Store.create(
                owner,
                "Tablo Dining",
                StoreContact.of("02-123-4567"),
                StoreAddress.of("Seoul-ro 1", "2F", "Seoul", "Gangnam"),
                "Modern Korean dining"
        );
        ReflectionTestUtils.setField(store, "id", 10L);

        customerPrincipal = AuthenticatedUser.from(customer);
        ownerPrincipal = AuthenticatedUser.from(owner);
    }

    @Test
    void createReservationCreatesPendingReservation() {
        LocalDateTime reservedAt = LocalDateTime.now().plusDays(1);
        CreateReservationRequest request = new CreateReservationRequest(store.getId(), 2, reservedAt, "Window seat");

        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 100L);
            return saved;
        });

        var response = reservationService.createReservation(customerPrincipal, request);

        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.status()).isEqualTo(ReservationStatus.PENDING.name());
        assertThat(response.partySize()).isEqualTo(2);
        assertThat(response.store().id()).isEqualTo(store.getId());
    }

    @Test
    void cancelMyReservationCancelsPendingReservation() {
        Reservation reservation = Reservation.create(customer, store, null, 4, LocalDateTime.now().plusHours(3), "Birthday");
        ReflectionTestUtils.setField(reservation, "id", 200L);

        when(reservationRepository.findByIdAndUserId(200L, customer.getId())).thenReturn(Optional.of(reservation));

        var response = reservationService.cancelMyReservation(customerPrincipal, 200L);

        assertThat(response.status()).isEqualTo(ReservationStatus.CANCELED.name());
        assertThat(response.canceledAt()).isNotNull();
    }

    @Test
    void getOwnerReservationsRejectsNonOwnerAccess() {
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));

        assertThatThrownBy(() -> reservationService.getOwnerReservations(customerPrincipal, store.getId(), null))
                .isInstanceOf(DomainException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FORBIDDEN);
    }

    @Test
    void updateOwnerReservationStatusConfirmsReservation() {
        Reservation reservation = Reservation.create(customer, store, null, 2, LocalDateTime.now().plusHours(2), null);
        ReflectionTestUtils.setField(reservation, "id", 300L);

        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
        when(reservationRepository.findById(300L)).thenReturn(Optional.of(reservation));

        var response = reservationService.updateOwnerReservationStatus(
                ownerPrincipal,
                store.getId(),
                300L,
                new UpdateReservationStatusRequest("confirmed")
        );

        assertThat(response.status()).isEqualTo(ReservationStatus.CONFIRMED.name());
        verify(reservationRepository).findById(300L);
    }
}
