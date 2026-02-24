package com.example.company_profile.domain.usecase.shop

import com.example.company_profile.domain.model.shop.ShopItem
import com.example.company_profile.domain.repository.shop.ShopRepository
import javax.inject.Inject

class CreateShopItemUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(
        shopId: String,
        name: String,
        price: String,
        sizes: List<String>,
        localImageUri: String,
        category : String

    ): Result<ShopItem> {
        val newItem = ShopItem(
            id = "",
            shopId = shopId,
            name = name,
            price = price,
            imageUrl = "",
            category = "All",
            sizes = sizes
        )
        return repository.createItem(newItem, localImageUri)
    }
}