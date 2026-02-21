package com.example.shop_feed.domain.usecase

import com.example.shop_feed.domain.model.Shop
import com.example.shop_feed.domain.repository.ShopRepository
import javax.inject.Inject


class GetShopsUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(): Result<List<Shop>> = repository.getShops()
}