package com.example.item_feed.data.remote

import com.example.item_feed.data.dto.ItemDto
import com.example.item_feed.data.dto.ShopIdDto
import com.example.item_feed.data.dto.CartItemRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AllItemApiService {

    @GET("shops")
    suspend fun getShops(): List<ShopIdDto>

    @GET("shops/{shopId}/items")
    suspend fun getItemsForShop(@Path("shopId") shopId: String): List<ItemDto>

    @POST("cart")
    suspend fun addToCart(@Body cartItem: CartItemRequestDto)
}