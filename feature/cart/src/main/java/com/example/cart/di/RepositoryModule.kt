package com.example.cart.di

import com.example.cart.data.repository.CartRepositoryImpl
import com.example.cart.domain.repository.CartRepository
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
    abstract fun bindCartRepository(
        impl : CartRepositoryImpl
    ) : CartRepository
}