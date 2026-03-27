package org.example.tablo.store.controller;

import java.util.List;

import org.example.tablo.common.response.ApiResponse;
import org.example.tablo.common.response.SuccessCode;
import org.example.tablo.store.dto.StoreDetailResponse;
import org.example.tablo.store.dto.StoreSummaryResponse;
import org.example.tablo.store.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ApiResponse<List<StoreSummaryResponse>> getStores() {
        return ApiResponse.of(SuccessCode.STORE_LIST_SUCCESS, storeService.getStores());
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> getStore(@PathVariable Long storeId) {
        return ApiResponse.of(SuccessCode.STORE_DETAIL_SUCCESS, storeService.getStore(storeId));
    }
}
