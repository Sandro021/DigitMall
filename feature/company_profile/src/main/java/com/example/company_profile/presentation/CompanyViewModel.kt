package com.example.company_profile.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.company_profile.domain.usecase.reels.GetReelsUseCase
import com.example.company_profile.domain.usecase.reels.UploadReelUseCase
import com.example.company_profile.domain.usecase.shop.CreateShopItemUseCase
import com.example.company_profile.domain.usecase.shop.CreateShopUseCase
import com.example.company_profile.domain.usecase.shop.GetShopItemsUseCase
import com.example.company_profile.domain.usecase.shop.GetShopsUseCase
import com.example.company_profile.presentation.contract.CompanyEffect
import com.example.company_profile.presentation.contract.CompanyIntent
import com.example.company_profile.presentation.contract.CompanyState
import com.example.company_profile.presentation.mapper.toUiModel
import com.example.data.CurrentUserProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val currentUserProvider: CurrentUserProvider,
    private val getShopsUseCase: GetShopsUseCase,
    private val createShopUseCase: CreateShopUseCase,
    private val getShopItemsUseCase: GetShopItemsUseCase,
    private val createShopItemUseCase: CreateShopItemUseCase,
    private val getReelsUseCase: GetReelsUseCase,
    private val uploadReelUseCase: UploadReelUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CompanyState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CompanyEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleIntent(CompanyIntent.LoadCompanyData)
    }

    fun handleIntent(intent: CompanyIntent) {
        when (intent) {
            is CompanyIntent.LoadCompanyData -> loadData()
            is CompanyIntent.CreateShop -> createShop(intent)
            is CompanyIntent.UploadReel -> uploadReel(intent)
            is CompanyIntent.AddShopItem -> addShopItem(intent)
        }
    }

    private fun loadData() {
        val userId = currentUserProvider.currentUserId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getShopsUseCase(userId).onSuccess { domainShops ->
                _state.update { it.copy(shops = domainShops.map { shop -> shop.toUiModel() }) }

                domainShops.forEach { shop ->
                    getShopItemsUseCase(shop.id).onSuccess { items ->
                        val currentItems = _state.value.shopItems.toMutableMap()
                        currentItems[shop.id] = items.map { it.toUiModel() }
                        _state.update { it.copy(shopItems = currentItems) }
                    }
                }
            }.onFailure { error ->
                _state.update { it.copy(error = error.message) }
            }

            getReelsUseCase(userId).onSuccess { domainReels ->
                _state.update { it.copy(reels = domainReels.map { it.toUiModel() }) }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun uploadReel(intent: CompanyIntent.UploadReel) {
        val userId = currentUserProvider.currentUserId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _effect.send(CompanyEffect.ShowToast("Uploading Reel... Please wait."))

            uploadReelUseCase(
                authorProfileId = userId,
                videoUriString = intent.videoUri,
                caption = intent.caption,
                hashtags = emptyList()
            ).onSuccess {
                _effect.send(CompanyEffect.ShowToast("Reel uploaded successfully!"))
                loadData()
            }.onFailure {
                _effect.send(CompanyEffect.ShowToast("Failed to upload Reel."))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun addShopItem(intent: CompanyIntent.AddShopItem) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _effect.send(CompanyEffect.ShowToast("Uploading Item..."))


            createShopItemUseCase(
                shopId = intent.shopId,
                name = intent.name,
                price = intent.price,
                localImageUri = intent.imageUri,
                sizes = intent.sizes,
                category = intent.category

            ).onSuccess {
                _effect.send(CompanyEffect.ShowToast("Item added!"))
                loadData()
            }.onFailure {
                _effect.send(CompanyEffect.ShowToast("Failed to add item."))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun createShop(intent: CompanyIntent.CreateShop) {
        val userId = currentUserProvider.currentUserId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _effect.send(CompanyEffect.ShowToast("Creating Shop..."))

            createShopUseCase(
                companyProfileId = userId,
                name = intent.name,
                description = intent.description,
                location = intent.location,
                localImageUri = intent.imageUri
            ).onSuccess {
                _effect.send(CompanyEffect.ShowToast("Shop created successfully!"))
                loadData()
            }.onFailure {
                _effect.send(CompanyEffect.ShowToast("Failed to create shop."))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}