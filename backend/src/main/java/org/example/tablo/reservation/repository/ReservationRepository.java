package org.example.tablo.reservation.repository;

import java.util.List;
import java.util.Optional;

import org.example.tablo.reservation.domain.Reservation;
import org.example.tablo.reservation.domain.ReservationStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = {"store", "table", "user"})
    List<Reservation> findAllByUserIdOrderByReservedAtDesc(Long userId);

    @EntityGraph(attributePaths = {"store", "table", "user"})
    Optional<Reservation> findByIdAndUserId(Long id, Long userId);

    @EntityGraph(attributePaths = {"store", "table", "user"})
    List<Reservation> findAllByStoreIdOrderByReservedAtAsc(Long storeId);

    @EntityGraph(attributePaths = {"store", "table", "user"})
    List<Reservation> findAllByStoreIdAndStatusOrderByReservedAtAsc(Long storeId, ReservationStatus status);

    @Override
    @EntityGraph(attributePaths = {"store", "table", "user"})
    Optional<Reservation> findById(Long id);
}
