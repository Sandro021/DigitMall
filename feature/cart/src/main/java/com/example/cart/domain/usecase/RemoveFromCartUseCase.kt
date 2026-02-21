package com.example.cart.domain.usecase

import com.example.cart.domain.repository.CartRepository
import javax.inject.Inject


class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: String): Result<Unit> {
        return cartRepository.removeFromCart(cartItemId)
    }
}