package com.example.company_profile.domain.usecase.shop

import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.repository.shop.ShopRepository
import javax.inject.Inject

class CreateShopUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(
        companyProfileId: String,
        name: String,
        description: String,
        location: String,
        localImageUri: String
    ): Result<Shop> {
        val newShop = Shop(
            id = "",
            companyProfileId = companyProfileId,
            name = name,
            description = description,
            location = location,
            imageUrl = ""
        )

        return repository.createShop(newShop, localImageUri)
    }
}