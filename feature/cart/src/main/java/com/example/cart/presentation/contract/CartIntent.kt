package com.example.cart.presentation.contract

sealed class CartIntent {
    object LoadCart : CartIntent()
    data class RemoveItem(val id: String) : CartIntent()

    object ClearCart : CartIntent()
}