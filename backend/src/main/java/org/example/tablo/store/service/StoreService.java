package org.example.tablo.store.service;

import java.util.Comparator;
import java.util.List;

import org.example.tablo.common.exception.ErrorCode;
import org.example.tablo.store.domain.Store;
import org.example.tablo.store.domain.StoreDomainException;
import org.example.tablo.store.dto.StoreDetailResponse;
import org.example.tablo.store.dto.StoreSummaryResponse;
import org.example.tablo.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<StoreSummaryResponse> getStores() {
        return storeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Store::getId))
                .map(store -> new StoreSummaryResponse(
                        store.getId(),
                        store.getName(),
                        store.getContact().getPhoneNumber(),
                        store.getAddress().getCity(),
                        store.getAddress().getDistrict(),
                        store.getDescription()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public StoreDetailResponse getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreDomainException(ErrorCode.STORE_NOT_FOUND));

        return new StoreDetailResponse(
                store.getId(),
                store.getOwner().getId(),
                store.getName(),
                store.getContact().getPhoneNumber(),
                store.getAddress().getAddressLine1(),
                store.getAddress().getAddressLine2(),
                store.getAddress().getCity(),
                store.getAddress().getDistrict(),
                store.getDescription()
        );
    }
}
