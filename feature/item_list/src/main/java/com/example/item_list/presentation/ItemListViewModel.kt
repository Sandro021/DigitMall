package com.example.item_list.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cart.domain.usecase.AddToCartUseCase
import com.example.data.CurrentUserProvider
import com.example.item_list.domain.usecase.GetShopItemsUseCase
import com.example.item_list.presentation.contract.ItemListIntent
import com.example.item_list.presentation.contract.ItemListState
import com.example.item_list.presentation.mapper.toDomain
import com.example.item_list.presentation.mapper.toShopItem
import com.example.item_list.presentation.mapper.toUiList
import com.example.item_list.presentation.model.ItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getShopItemsUseCase: GetShopItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ItemListState())
    val state: StateFlow<ItemListState> = _state.asStateFlow()


    fun handleIntent(intent: ItemListIntent) {
        when (intent) {
            is ItemListIntent.LoadItems -> loadItems(intent.shopId)
            is ItemListIntent.SelectCategory -> filterByCategory(intent.category)
            is ItemListIntent.AddToCart -> addToCart(intent.item, intent.selectedSize)

        }
    }

    private fun loadItems(shopId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getShopItemsUseCase(shopId)
                .onSuccess { items ->

                    val categories = listOf("All") + items.map { it.category }.distinct()

                    _state.update {
                        it.copy(
                            isLoading = false,
                            items = items.toUiList(),
                            displayedItems = items.toUiList(),
                            categories = categories
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }


    private fun filterByCategory(category: String) {
        _state.update { currentState ->
            val filtered = if (category == "All") {
                currentState.items
            } else {
                currentState.items.filter { it.category == category }
            }
            currentState.copy(
                selectedCategory = category,
                displayedItems = filtered
            )
        }
    }

    private fun addToCart(item: ItemUi, size: String) {
        viewModelScope.launch {
            _state.update { it.copy(cartCount = it.cartCount + 1) }

            val userId = currentUserProvider.currentUserId

            if (userId == null) {
                _state.update { it.copy(error = "User not logged in") }
                return@launch
            }

            addToCartUseCase(userId, item.toShopItem().toDomain(), size)
                .onSuccess {

                }
                .onFailure {

                    _state.update {
                        it.copy(
                            cartCount = it.cartCount - 1,
                            error = "Failed to add to cart"
                        )
                    }
                }
        }
    }
}