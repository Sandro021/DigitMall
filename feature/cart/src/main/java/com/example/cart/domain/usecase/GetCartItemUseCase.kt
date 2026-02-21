package com.example.cart.domain.usecase

import com.example.cart.domain.model.CartItem
import com.example.cart.domain.repository.CartRepository
import javax.inject.Inject


class GetCartItemUseCase @Inject constructor(
    private val repo: CartRepository
) {
    suspend operator fun invoke(userId: String): Result<List<CartItem>> = repo.getCartItems(userId)
}