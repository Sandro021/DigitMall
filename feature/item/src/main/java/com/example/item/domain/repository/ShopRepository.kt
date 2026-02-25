package com.example.item.domain.repository

import com.example.item.data.remote.dto.CartDto
import com.example.item.domain.model.Item
import com.example.item.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    fun getItemById(shopId: String, itemId: String): Flow<Resource<Item>>
    fun addToCart(cart: CartDto): Flow<Resource<CartDto>>
}