package com.example.shop_feed.domain.repository

import com.example.shop_feed.domain.model.Shop

interface ShopRepository {
    suspend fun getShops(): Result<List<Shop>>
}