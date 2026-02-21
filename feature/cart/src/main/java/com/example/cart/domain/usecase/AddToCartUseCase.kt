package com.example.cart.domain.usecase

import com.example.cart.domain.model.CartItem
import com.example.cart.domain.model.ShopItem
import com.example.cart.domain.repository.CartRepository
import javax.inject.Inject


class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        userId: String,
        shopItem: ShopItem,
        selectedSize: String
    ): Result<Unit> {


        val cartItem = CartItem(
            userId = userId,
            itemId = shopItem.id,
            name = shopItem.name,
            price = shopItem.price,
            image = shopItem.image,
            size = selectedSize,
            quantity = 1
        )
        return cartRepository.addToCart(cartItem)
    }
}