package com.example.cart.presentation.mapper

import com.example.cart.domain.model.CartItem
import com.example.cart.presentation.model.CartItemUi

fun CartItem.toUi(): CartItemUi {
    val rawPrice = this.price.replace("$", "").trim().toDoubleOrNull() ?: 0.0

    val totalValue = rawPrice * this.quantity

    return CartItemUi(
        id = this.id,
        itemId = this.itemId,
        name = this.name,
        imageUrl = this.image,
        size = this.size,
        quantity = this.quantity,
        singlePriceDisplay = formatPrice(rawPrice),
        totalPriceDisplay = formatPrice(totalValue)
    )
}

private fun formatPrice(amount: Double): String {
    return "$%.2f".format(amount)
}

fun List<CartItem>.toUiList(): List<CartItemUi> {
    return this.map { it.toUi() }
}