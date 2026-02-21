package com.example.cart.data.remote

import com.example.cart.data.dto.CartItemDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiService {
    @GET("cart")
    suspend fun getCartItems(@Query("userId") userId: String): List<CartItemDto>

    @POST("cart")
    suspend fun addToCart(@Body item: CartItemDto)

    @DELETE("cart/{id}")
    suspend fun deleteCartItem(@Path("id") id: String)
}