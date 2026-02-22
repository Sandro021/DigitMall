package com.example.di

import com.example.data.CurrentUserProvider
import com.example.data.auth.FirebaseAuthProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthProviderModule {

    @Binds
    abstract fun bindCurrentUserProvider(
        firebaseAuthProvider: FirebaseAuthProvider
    ): CurrentUserProvider
}