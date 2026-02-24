package com.example.company_profile.data.remote.shop

import com.example.company_profile.data.dto.shop.ShopDto
import com.example.company_profile.data.dto.shop.ShopItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CompanyShopApiService {
    @GET("shops")
    suspend fun getShopsByCompany(@Query("companyProfileId") companyId: String): List<ShopDto>

    @POST("shops")
    suspend fun createShop(@Body shop: ShopDto): ShopDto

    @GET("items")
    suspend fun getItemsByShop(@Query("shopId") shopId: String): List<ShopItemDto>

    @POST("items")
    suspend fun createItem(@Body item: ShopItemDto): ShopItemDto
}