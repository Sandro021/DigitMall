package com.example.item.presentation

import com.example.item.domain.model.Item

object ItemContract {

    data class State(
        val isLoading: Boolean = false,
        val item: Item? = null,
        val selectedSize: String? = null,
        val error: String? = null
    )

    sealed interface Event {
        data class Load(val shopId: String, val itemId: String) : Event
        data class SelectSize(val size: String) : Event
        object AddToCart : Event
    }

    sealed interface Effect {
        data class ShowToast(val message: String) : Effect
    }
}