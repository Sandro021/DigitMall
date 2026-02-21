package com.example.di




import com.example.shop_feed.data.remote.ShopApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://697debce97386252a26960a0.mockapi.io/"

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideShopApiService(retrofit: Retrofit): ShopApiService =
        retrofit.create(ShopApiService::class.java)
//
//
//    @Provides
//    @Singleton
//    fun provideItemApiService(retrofit: Retrofit): ItemApiService =
//        retrofit.create(ItemApiService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideCartApiService(retrofit: Retrofit): CartApiService =
//        retrofit.create(CartApiService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideAllItemApiService(retrofit: Retrofit): AllItemApiService =
//        retrofit.create(AllItemApiService::class.java)

}