package com.example.item_feed.presentation.all_item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CurrentUserProvider
import com.example.item_feed.domain.model.Item
import com.example.item_feed.domain.usecase.AddToCartUseCase
import com.example.item_feed.domain.usecase.GetAllItemsUseCase
import com.example.item_feed.presentation.all_item.contract.AllItemsIntent
import com.example.item_feed.presentation.all_item.contract.AllItemsState
import com.example.item_feed.presentation.all_item.mapper.toUiModel
import com.example.item_feed.presentation.all_item.model.AllItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _state = MutableStateFlow(AllItemsState())
    val state: StateFlow<AllItemsState> = _state.asStateFlow()

    private var currentDomainItems: List<Item> = emptyList()

    init {
        handleIntent(AllItemsIntent.LoadItems)
    }

    fun handleIntent(intent: AllItemsIntent) {
        when (intent) {
            is AllItemsIntent.LoadItems -> fetchItems()

            is AllItemsIntent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = intent.category) }
                fetchItems()
            }

            is AllItemsIntent.ChangeSortOrder -> {
                _state.update { it.copy(sortOrder = intent.sortOrder) }
                fetchItems()
            }

            is AllItemsIntent.AddToCart -> {
                addToCart(intent.item)
            }

            is AllItemsIntent.ClearCartMessage -> {
                _state.update { it.copy(cartMessage = null) }
            }
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            val currentState = _state.value

            getAllItemsUseCase(
                categoryFilter = currentState.selectedCategory,
                sortOrder = currentState.sortOrder
            )
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e ->
                    Log.e("API_CRASH", "CRASH REASON: ${e.message}", e)

                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { domainItems ->

                    Log.d("API_SUCCESS", "Successfully downloaded ${domainItems.size} items!")
                    currentDomainItems = domainItems

                    val uniqueCategories = domainItems.map { it.category }.distinct()
                    val allCategories = listOf("All") + uniqueCategories

                    _state.update {
                        it.copy(
                            isLoading = false,
                            items = domainItems.map { item -> item.toUiModel() },
                            categories = if (currentState.selectedCategory == "All") allCategories else it.categories
                        )
                    }
                }
        }
    }

    private fun addToCart(uiItem: AllItemUi) {
        viewModelScope.launch {
            try {
                val domainItem = currentDomainItems.find { it.id == uiItem.id }

                if (domainItem != null) {


                    val userId = currentUserProvider.currentUserId

                    if (userId == null) {
                        _state.update { it.copy(error = "User not logged in") }
                        return@launch
                    }

                    addToCartUseCase(item = domainItem, userId = userId)

                    _state.update { it.copy(cartMessage = "Added ${uiItem.name} to cart!") }
                } else {
                    _state.update { it.copy(cartMessage = "Could not find item details.") }
                }

            } catch (e: Exception) {
                _state.update { it.copy(cartMessage = "Failed to add to cart: ${e.message}") }
            }
        }
    }
}