package com.example.item.domain.usecase

import com.example.item.data.remote.dto.CartDto
import com.example.item.domain.repository.ShopRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repo: ShopRepository
) {
    operator fun invoke(cart: CartDto) = repo.addToCart(cart)
}