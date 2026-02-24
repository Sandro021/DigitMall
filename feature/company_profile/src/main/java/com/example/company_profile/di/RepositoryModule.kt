package com.example.company_profile.di

import com.example.company_profile.data.repository.reels.ReelsRepositoryImpl
import com.example.company_profile.data.repository.shop.ShopRepositoryImpl
import com.example.company_profile.domain.repository.reels.ReelsRepository
import com.example.company_profile.domain.repository.shop.ShopRepository
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
    abstract fun bindReelsRepository(
        impl: ReelsRepositoryImpl
    ): ReelsRepository


    @Binds
    @Singleton
    abstract fun bindShopRepository(
        impl: ShopRepositoryImpl
    ): ShopRepository
}
