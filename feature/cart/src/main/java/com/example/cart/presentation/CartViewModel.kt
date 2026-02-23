package com.example.cart.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cart.domain.usecase.GetCartItemUseCase
import com.example.cart.domain.usecase.RemoveFromCartUseCase
import com.example.cart.presentation.contract.CartIntent
import com.example.cart.presentation.contract.CartState
import com.example.cart.presentation.mapper.toUiList
import com.example.data.CurrentUserProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()


    init {
        handleIntent(CartIntent.LoadCart)
    }

    fun handleIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.LoadCart -> loadCartItems()
            is CartIntent.RemoveItem -> deleteItem(intent.id)
            is CartIntent.ClearCart -> clearAllItems()
        }
    }

    private fun loadCartItems() {
        viewModelScope.launch {

            val userId = currentUserProvider.currentUserId

            if (userId == null) {
                _state.update { it.copy(error = "User not logged in") }
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            getCartItemsUseCase(userId)
                .onSuccess { items ->

                    val total = items.sumOf {
                        (it.price.toDoubleOrNull() ?: 0.0) * it.quantity
                    }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            cartItems = items.toUiList(),
                            totalPrice = total
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun deleteItem(cartItemId: String) {
        viewModelScope.launch {

            val currentList = _state.value.cartItems
            val updatedList = currentList.filter { it.id != cartItemId }


            val newTotal = updatedList.sumOf {
                (it.singlePriceDisplay.toDoubleOrNull() ?: 0.0) * it.quantity
            }

            _state.update {
                it.copy(cartItems = updatedList, totalPrice = newTotal)
            }


            removeFromCartUseCase(cartItemId)
                .onFailure {

                    _state.update {
                        it.copy(
                            cartItems = currentList,
                            error = "Failed to delete item"
                        )
                    }

                    loadCartItems()
                }
        }
    }

    private fun clearAllItems() {
        viewModelScope.launch {
            val itemsToDelete = _state.value.cartItems

            _state.update {
                it.copy(
                    cartItems = emptyList(),
                    totalPrice = 0.0
                )
            }

            itemsToDelete.forEach { item ->
                removeFromCartUseCase(item.id).onFailure { error ->

                }
            }
        }
    }
}