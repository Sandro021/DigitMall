package com.example.item.data.repository

import com.example.item.data.remote.common.HandleResponse
import com.example.item.data.remote.dto.CartDto
import com.example.item.data.remote.mapper.toDomain
import com.example.item.data.remote.service.ShopApi
import com.example.item.domain.model.Item
import com.example.item.domain.model.Resource
import com.example.item.domain.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: ShopApi
) : ShopRepository {

    override fun getItemById(
        shopId: String,
        itemId: String
    ): Flow<Resource<Item>> {

        return HandleResponse.safeApiCall {
            api.getItems(shopId)
        }.map { result ->

            when (result) {

                is Resource.Success -> {
                    val item = result.data
                        .firstOrNull { it.id == itemId }
                        ?.toDomain()

                    if (item != null) {
                        Resource.Success<Item>(item)
                    } else {
                        Resource.Error<Item>("Item not found")
                    }
                }

                is Resource.Error -> {
                    Resource.Error<Item>(result.message)
                }

                is Resource.Loader -> {
                    Resource.Loader<Item>(result.isLoading)
                }
            }
        }
    }

    override fun addToCart(
        cart: CartDto
    ): Flow<Resource<CartDto>> {

        return HandleResponse.safeApiCall {
            api.addToCart(cart)
        }.map { result ->

            when (result) {
                is Resource.Success -> Resource.Success(result.data)
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loader -> Resource.Loader(result.isLoading)
            }
        }
    }
}