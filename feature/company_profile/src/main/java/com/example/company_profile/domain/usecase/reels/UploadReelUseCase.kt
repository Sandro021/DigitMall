package com.example.company_profile.domain.usecase.reels

import com.example.company_profile.domain.model.reels.Reel
import com.example.company_profile.domain.repository.reels.ReelsRepository
import javax.inject.Inject

class UploadReelUseCase @Inject constructor(
    private val repository: ReelsRepository
) {
    suspend operator fun invoke(
        authorProfileId: String,
        videoUriString: String,
        caption: String,
        hashtags: List<String>
    ): Result<Reel> {
        return runCatching {
            val videoUrl = repository.uploadVideo(videoUriString).getOrThrow()


            val newReel = Reel(
                id = "",
                authorProfileId = authorProfileId,
                videoUrl = videoUrl,
                coverUrl = "https://via.placeholder.com/400x700",
                caption = caption,
                hashtags = hashtags,
                likesCount = 0,
                commentsCount = 0,
                isLiked = false,
                createdAt = System.currentTimeMillis()
            )

            repository.createReel(newReel).getOrThrow()
        }
    }
}