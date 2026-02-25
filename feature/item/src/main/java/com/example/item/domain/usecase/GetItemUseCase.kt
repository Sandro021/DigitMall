package com.example.item.domain.usecase

import com.example.item.domain.repository.ShopRepository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val repo: ShopRepository
) {
    operator fun invoke(shopId: String, itemId: String) = repo.getItemById(shopId, itemId)
}
