package com.example.item.di

import com.example.item.data.repository.ShopRepositoryImpl
import com.example.item.domain.repository.ShopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule{
    @Binds
    @Singleton
    abstract fun ShopRepository(
        impl: ShopRepositoryImpl
    ): ShopRepository
}