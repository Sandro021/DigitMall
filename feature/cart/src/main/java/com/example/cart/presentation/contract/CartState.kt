package com.example.cart.presentation.contract

import com.example.cart.presentation.model.CartItemUi

data class CartState (
    val isLoading: Boolean = false,
    val cartItems: List<CartItemUi> = emptyList(),
    val totalPrice: Double = 0.0,
    val error: String? = null
)