package com.example.company_profile.presentation.contract

import com.example.company_profile.presentation.model.ReelUiModel
import com.example.company_profile.presentation.model.ShopItemUiModel
import com.example.company_profile.presentation.model.ShopUiModel

data class CompanyState(
    val isLoading: Boolean = false,
    val shops: List<ShopUiModel> = emptyList(),
    val reels: List<ReelUiModel> = emptyList(),
    val shopItems: Map<String, List<ShopItemUiModel>> = emptyMap(),
    val error: String? = null
)
