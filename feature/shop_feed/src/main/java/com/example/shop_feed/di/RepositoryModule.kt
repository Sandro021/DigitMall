package com.example.shop_feed.di

import com.example.shop_feed.data.repository.ShopRepositoryImpl
import com.example.shop_feed.domain.repository.ShopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindShopRepository(
        impl: ShopRepositoryImpl
    ): ShopRepository

}