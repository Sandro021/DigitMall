package com.example.shop_feed.data.remote

import com.example.shop_feed.data.dto.ShopDto
import retrofit2.http.GET

interface ShopApiService {
    @GET("shops")
    suspend fun getShops(): List<ShopDto>
}