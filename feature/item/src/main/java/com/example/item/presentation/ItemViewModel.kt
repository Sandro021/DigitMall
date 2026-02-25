package com.example.item.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.item.data.remote.dto.CartDto
import com.example.item.domain.model.Resource
import com.example.item.domain.usecase.AddToCartUseCase
import com.example.item.domain.usecase.GetItemUseCase
import com.example.ui.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItem: GetItemUseCase,
    private val addToCart: AddToCartUseCase
) : BaseViewModel<ItemContract.State, ItemContract.Event>(ItemContract.State()) {

    private val _effect = Channel<ItemContract.Effect>()
    val effect = _effect.receiveAsFlow()

    override fun onEvent(event: ItemContract.Event) {
        when (event) {
            is ItemContract.Event.Load -> loadItem(event.shopId, event.itemId)
            is ItemContract.Event.SelectSize -> updateState { it.copy(selectedSize = event.size) }
            ItemContract.Event.AddToCart -> addToCart()
        }
    }

    private fun loadItem(shopId: String, itemId: String) {
        viewModelScope.launch {
            getItem(shopId, itemId).collect { res ->
                when (res) {
                    is Resource.Loader -> updateState { it.copy(isLoading = res.isLoading) }
                    is Resource.Success -> updateState { it.copy(item = res.data) }
                    is Resource.Error -> updateState { it.copy(error = res.message) }
                }
            }
        }
    }

    private fun addToCart() {
        val item = state.value.item ?: return
        val size = state.value.selectedSize ?: return

        viewModelScope.launch {
            addToCart(
                CartDto(
                    userId = "J4aJukKG47RHMfS2bcHIr414uU43",
                    itemId = item.id,
                    price = item.price.toString(),
                    image = item.image,
                    size = size,
                    quantity = 1,
                    name = item.name
                )
            ).collect { res ->
                if (res is Resource.Success) {
                    _effect.send(ItemContract.Effect.ShowToast("Added to cart"))
                }
            }
        }
    }
}