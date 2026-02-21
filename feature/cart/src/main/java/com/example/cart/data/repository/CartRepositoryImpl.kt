package com.example.cart.data.repository

import com.example.cart.data.mapper.toDomain
import com.example.cart.data.mapper.toDto
import com.example.cart.data.remote.CartApiService
import com.example.cart.domain.model.CartItem
import com.example.cart.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: CartApiService
) : CartRepository {
    override suspend fun getCartItems(userId: String): Result<List<CartItem>> = runCatching {
        api.getCartItems(userId).map { it.toDomain() }
    }

    override suspend fun addToCart(cartItem: CartItem): Result<Unit> = runCatching {
        api.addToCart(cartItem.toDto())
    }

    override suspend fun removeFromCart(cartItemId: String): Result<Unit> = runCatching {
        api.deleteCartItem(cartItemId)
    }
}