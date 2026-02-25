package com.example.item.data.remote.service

import com.example.item.data.remote.dto.CartDto
import com.example.item.data.remote.dto.ItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShopApi {
    @GET("shops/{shopId}/items")
    suspend fun getItems(@Path("shopId") shopId: String): Response<List<ItemDto>>

    @POST("cart")
    suspend fun addToCart(@Body cart: CartDto): Response<CartDto>
}