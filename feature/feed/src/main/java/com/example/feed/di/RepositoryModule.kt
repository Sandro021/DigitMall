package com.example.feed.di

import android.content.Context
import com.example.feed.data.local.UserInteractionStore
import com.example.feed.data.remote.service.FeedApi
import com.example.feed.data.repository.CommentsRepositoryImpl
import com.example.feed.data.repository.FeedRepositoryImpl
import com.example.feed.domain.repository.CommentsRepository
import com.example.feed.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides @Singleton
    fun provideFeedRepository(api: FeedApi): FeedRepository =
        FeedRepositoryImpl(api)

    @Provides @Singleton
    fun provideUserInteractionStore(@ApplicationContext context: Context): UserInteractionStore =
        UserInteractionStore(context)

    @Provides @Singleton
    fun provideCommentRepository(api: FeedApi): CommentsRepository =
        CommentsRepositoryImpl(api)
}