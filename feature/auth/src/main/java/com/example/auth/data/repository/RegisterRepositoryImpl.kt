package com.example.auth.data.repository

import com.example.auth.data.remote.dto.ProfileDto
import com.example.auth.data.remote.service.ProfileApi
import com.example.auth.domain.model.AccountType
import com.example.auth.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.substringBefore

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val profileApi: ProfileApi
) : RegisterRepository {

    override suspend fun register(
        accountType: AccountType,
        email: String,
        password: String,
        userFirstName: String?,
        userLastName: String?,
        phone: String?,
        companyName: String?,
        location: String?,
        description: String?
    ) {
        // 1) Firebase create user
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        val uid = firebaseAuth.currentUser?.uid
            ?: throw kotlin.IllegalStateException("Firebase uid is null after register")

        // 2) Save profile in MockAPI
        val username = email.substringBefore("@") // Generate username from email
        val dto = ProfileDto(
            id = uid,
            firebaseUid = uid,
            email = email,
            accountType = accountType.name,
            username = username,
            avatarUrl = "https://via.placeholder.com/150", // Default avatar
            firstName = userFirstName,
            lastName = userLastName,
            phone = phone,
            companyName = companyName,
            location = location,
            description = description
        )

        profileApi.createProfile(dto)
    }
}