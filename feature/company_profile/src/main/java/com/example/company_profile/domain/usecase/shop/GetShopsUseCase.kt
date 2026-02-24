package com.example.company_profile.domain.usecase.shop

import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.repository.shop.ShopRepository
import javax.inject.Inject

class GetShopsUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    suspend operator fun invoke(companyId: String): Result<List<Shop>> {
        return repository.getShops(companyId)
    }
}