package com.example.auth.data.repository

import com.example.auth.data.remote.mapper.toDomain
import com.example.auth.data.remote.service.ProfileApi
import com.example.auth.domain.model.Profile
import com.example.auth.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.firstOrNull

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val profileApi: ProfileApi
) : LoginRepository {

    override suspend fun login(email: String, password: String): Profile {
        // 1) Firebase login
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

        val uid = firebaseAuth.currentUser?.uid
            ?: throw kotlin.IllegalStateException("Firebase uid is null after login")

        // 2) Fetch profile from MockAPI by firebaseUid
        val profiles = profileApi.getProfilesByFirebaseUid(uid)
        val profileDto = profiles.firstOrNull()
            ?: throw kotlin.IllegalStateException("Profile not found in MockAPI for this user")

        return profileDto.toDomain()
    }
}