package com.example.profile.data.repository


import android.net.Uri
import com.example.profile.data.mapper.toDomain
import com.example.profile.data.mapper.toUpdatedDto
import com.example.profile.data.remote.UserProfileApiService
import com.example.profile.domain.model.UserProfile
import com.example.profile.domain.repository.ProfileRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: UserProfileApiService,
    private val firebaseStorage: FirebaseStorage
) : ProfileRepository {

    override suspend fun getProfile(userId: String): Result<UserProfile> {
        return runCatching {

            withContext(Dispatchers.IO) {

                val dtoList = api.getProfileByFirebaseUid(userId)

                if (dtoList.isEmpty()) {
                    throw Exception("User not found in MockApi database")
                }

                val userDto = dtoList.first()
                userDto.toDomain()
            }
        }
    }

    override suspend fun updateProfile(profile: UserProfile): Result<UserProfile> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val existingDto = api.getProfileById(profile.id)

                val updatedDto = profile.toUpdatedDto(existingDto)

                val savedDto = api.updateProfile(profile.id, updatedDto)

                savedDto.toDomain()
            }
        }
    }

    override suspend fun uploadAvatar(localUriString: String): Result<String> {
        return runCatching {
            withContext(Dispatchers.IO) {
                // Parse the string back into an Android Uri
                val imageUri = Uri.parse(localUriString)

                // Create a unique file name in Firebase Storage
                // e.g., "avatars/1704067200000_avatar.jpg"
                val fileName = "avatars/${System.currentTimeMillis()}_avatar.jpg"
                val storageRef = firebaseStorage.reference.child(fileName)

                // Upload the file
                storageRef.putFile(imageUri).await()

                // Fetch and return the public download URL
                val downloadUrl = storageRef.downloadUrl.await()
                downloadUrl.toString()
            }
        }
    }
}