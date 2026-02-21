package com.example.cart.domain.repository

import com.example.cart.domain.model.CartItem


interface CartRepository {
    suspend fun getCartItems(userId: String): Result<List<CartItem>>


    suspend fun addToCart(cartItem: CartItem): Result<Unit>


    suspend fun removeFromCart(cartItemId: String): Result<Unit>

}