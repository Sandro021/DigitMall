package com.example.shop_feed.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_feed.domain.usecase.GetShopsUseCase
import com.example.shop_feed.presentation.contract.ShopListIntent
import com.example.shop_feed.presentation.contract.ShopListState
import com.example.shop_feed.presentation.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val getShopsUseCase: GetShopsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShopListState())
    val state = _state.asStateFlow()

    init {
        handleIntent(ShopListIntent.LoadShops)
    }

    fun handleIntent(intent: ShopListIntent) {
        when (intent) {
            is ShopListIntent.LoadShops -> loadShops()
            is ShopListIntent.OnShopClicked -> {

            }
        }
    }


    private fun loadShops() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = getShopsUseCase()

            result.onSuccess { fetchedShops ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        shops = fetchedShops.map { it.toUiModel() }
                    )
                }
            }.onFailure { error ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            }
        }
    }
}