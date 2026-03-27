package org.example.tablo.store.repository;

import java.util.List;

import org.example.tablo.store.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Override
    @EntityGraph(attributePaths = "owner")
    List<Store> findAll();

    @Override
    @EntityGraph(attributePaths = "owner")
    java.util.Optional<Store> findById(Long id);
}
