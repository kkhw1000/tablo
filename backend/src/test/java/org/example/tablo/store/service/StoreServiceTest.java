package org.example.tablo.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.example.tablo.store.domain.Store;
import org.example.tablo.store.domain.StoreAddress;
import org.example.tablo.store.domain.StoreContact;
import org.example.tablo.store.repository.StoreRepository;
import org.example.tablo.user.domain.User;
import org.example.tablo.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void getStoresReturnsSummaries() {
        User owner = User.create("owner@test.com", "Owner", "010-1234-5678", "encoded", UserRole.OWNER);
        ReflectionTestUtils.setField(owner, "id", 1L);

        Store store = Store.create(
                owner,
                "Tablo Dining",
                StoreContact.of("02-123-4567"),
                StoreAddress.of("Seoul-ro 1", "2F", "Seoul", "Gangnam"),
                "Modern Korean dining"
        );
        ReflectionTestUtils.setField(store, "id", 10L);

        when(storeRepository.findAll()).thenReturn(List.of(store));
        when(storeRepository.findById(10L)).thenReturn(Optional.of(store));

        var stores = storeService.getStores();
        var detail = storeService.getStore(10L);

        assertThat(stores).hasSize(1);
        assertThat(stores.getFirst().name()).isEqualTo("Tablo Dining");
        assertThat(detail.ownerId()).isEqualTo(1L);
        assertThat(detail.city()).isEqualTo("Seoul");
    }
}
