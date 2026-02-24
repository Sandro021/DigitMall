package com.example.company_profile.data.repository.reels

import androidx.core.net.toUri
import com.example.company_profile.data.mapper.toDomain
import com.example.company_profile.data.mapper.toDto
import com.example.company_profile.data.remote.reels.ReelsApiService
import com.example.company_profile.domain.model.reels.Reel
import com.example.company_profile.domain.repository.reels.ReelsRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReelsRepositoryImpl @Inject constructor(
    private val api: ReelsApiService,
    private val firebaseStorage: FirebaseStorage
) : ReelsRepository {

    override suspend fun uploadVideo(localUriString: String): Result<String> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val videoUri = localUriString.toUri()

                val fileName = "reels/video_${System.currentTimeMillis()}.mp4"
                val storageRef = firebaseStorage.reference.child(fileName)

                storageRef.putFile(videoUri).await()

                val downloadUrl = storageRef.downloadUrl.await()
                downloadUrl.toString()
            }
        }
    }

    override suspend fun createReel(reel: Reel): Result<Reel> {
        return runCatching {
            withContext(Dispatchers.IO) {

                val savedDto = api.createReel(reel.toDto())
                savedDto.toDomain()
            }
        }
    }

    override suspend fun getReelsByCompany(authorProfileId: String): Result<List<Reel>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val dtoList = api.getReelsByAuthor(authorProfileId)
                dtoList.map { it.toDomain() }
            }
        }
    }
}