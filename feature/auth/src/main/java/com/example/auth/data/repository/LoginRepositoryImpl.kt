package com.example.auth.data.repository

import com.example.auth.data.remote.mapper.toDomain
import com.example.auth.data.remote.service.ProfileApi
import com.example.auth.domain.model.Profile
import com.example.auth.domain.model.Resource
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
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

        val uid = firebaseAuth.currentUser?.uid
            ?: throw kotlin.IllegalStateException("Firebase uid is null after login")

        val profiles = profileApi.getProfilesByFirebaseUid(uid)
        val profileDto = profiles.firstOrNull()
            ?: throw kotlin.IllegalStateException("Profile not found in MockAPI for this user")

        return profileDto.toDomain()
    }


    override suspend fun getProfileById(id: String): Resource<Profile> {
        return try {
            val responseList = profileApi.getProfileById(id)

            if (responseList.isNotEmpty()) {

                val userProfile = responseList.first().toDomain()

                Resource.Success(userProfile)
            } else {
                Resource.Error("User profile not found in database.")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}