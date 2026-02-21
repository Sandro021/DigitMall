package com.example.shop_feed.data.repository

import com.example.shop_feed.data.mapper.toDomain
import com.example.shop_feed.data.remote.ShopApiService
import com.example.shop_feed.domain.model.Shop
import com.example.shop_feed.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: ShopApiService
) : ShopRepository {

    override suspend fun getShops(): Result<List<Shop>> = runCatching {
        api.getShops().map { it.toDomain() }
    }
}