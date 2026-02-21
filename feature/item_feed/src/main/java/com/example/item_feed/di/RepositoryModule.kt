package com.example.item_feed.di

import com.example.item_feed.data.repository.AllItemsRepositoryImpl
import com.example.item_feed.domain.repository.AllItemsRepository
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
    abstract fun bindAllItemsRepository(
        impl: AllItemsRepositoryImpl
    ): AllItemsRepository
}