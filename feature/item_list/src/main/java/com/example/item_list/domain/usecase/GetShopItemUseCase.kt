package com.example.item_list.domain.usecase

import com.example.item_list.domain.model.Item
import com.example.item_list.domain.repository.ItemRepository
import javax.inject.Inject


class GetShopItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(shopId: String): Result<List<Item>> {

        return itemRepository.getItemsByShopId(shopId)
    }
}