package com.example.auth.di

import com.example.auth.data.remote.service.ProfileApi
import com.example.auth.data.repository.LoginRepositoryImpl
import com.example.auth.data.repository.RegisterRepositoryImpl
import com.example.auth.domain.repository.LoginRepository
import com.example.auth.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        profileApi: ProfileApi
    ): RegisterRepository = RegisterRepositoryImpl(firebaseAuth, profileApi)

    @Provides @Singleton
    fun provideLoginRepository(
        firebaseAuth: FirebaseAuth,
        profileApi: ProfileApi
    ): LoginRepository = LoginRepositoryImpl(firebaseAuth, profileApi)

}