package com.example.item_list.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.item_list.data.dto.ItemDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ItemApiService {

    @GET("shops/{id}/items")
    suspend fun getItems(@Path("id") shopId: String): List<ItemDto>

}