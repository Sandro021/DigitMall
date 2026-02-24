package com.example.company_profile.domain.repository.shop

import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.model.shop.ShopItem

interface ShopRepository {
    suspend fun createShop(shop: Shop, localImageUri: String): Result<Shop>
    suspend fun getShops(companyId: String): Result<List<Shop>>
    suspend fun createItem(item: ShopItem, localImageUri: String): Result<ShopItem>
    suspend fun getItems(shopId: String): Result<List<ShopItem>>
}