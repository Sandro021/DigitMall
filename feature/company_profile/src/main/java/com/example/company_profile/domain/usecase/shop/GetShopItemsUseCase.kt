package com.example.company_profile.domain.usecase.shop

import com.example.company_profile.domain.model.shop.ShopItem
import com.example.company_profile.domain.repository.shop.ShopRepository
import javax.inject.Inject

class GetShopItemsUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<List<ShopItem>> {
        return repository.getItems(shopId)
    }
}