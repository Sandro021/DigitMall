package com.example.di


import com.example.auth.data.remote.service.ProfileApi
import com.example.cart.data.remote.CartApiService
import com.example.company_profile.data.remote.reels.ReelsApiService
import com.example.company_profile.data.remote.shop.CompanyShopApiService
import com.example.feed.data.remote.service.FeedApi
import com.example.item_feed.data.remote.AllItemApiService
import com.example.item_list.data.remote.ItemApiService
import com.example.profile.data.remote.UserProfileApiService
import com.example.shop_feed.data.remote.ShopApiService
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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


    @Provides
    @Singleton
    fun provideItemApiService(retrofit: Retrofit): ItemApiService =
        retrofit.create(ItemApiService::class.java)

    @Provides
    @Singleton
    fun provideCartApiService(retrofit: Retrofit): CartApiService =
        retrofit.create(CartApiService::class.java)

    @Provides
    @Singleton
    fun provideAllItemApiService(retrofit: Retrofit): AllItemApiService =
        retrofit.create(AllItemApiService::class.java)

    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi =
        retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFeedApi(retrofit: Retrofit): FeedApi =
        retrofit.create(FeedApi::class.java)

    @Provides
    @Singleton
    fun provideUserProfileApiService(retrofit: Retrofit): UserProfileApiService =
        retrofit.create(UserProfileApiService::class.java)

    @Provides
    @Singleton
    fun provideReelsApiService(retrofit: Retrofit): ReelsApiService =
        retrofit.create(ReelsApiService::class.java)

    @Provides
    @Singleton
    fun provideCompanyShopApiService(retrofit: Retrofit): CompanyShopApiService =
        retrofit.create(CompanyShopApiService::class.java)
}