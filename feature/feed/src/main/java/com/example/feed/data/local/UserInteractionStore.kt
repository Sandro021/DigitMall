package com.example.feed.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.feed.domain.model.UserInteraction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_interactions")

@Singleton
class UserInteractionStore @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore

    fun getUserInteraction(reelId: String): Flow<UserInteraction?> {
        return dataStore.data.map { preferences ->
            val watchedPercent = preferences[floatPreferencesKey("watched_$reelId")] ?: 0f
            val completedWatch = preferences[booleanPreferencesKey("completed_$reelId")] ?: false
            val liked = preferences[booleanPreferencesKey("liked_$reelId")] ?: false
            val returnedToPrevious = preferences[booleanPreferencesKey("returned_$reelId")] ?: false
            val lastWatchedAt = preferences[longPreferencesKey("last_watched_$reelId")] ?: System.currentTimeMillis()

            if (watchedPercent > 0f || completedWatch || liked || returnedToPrevious) {
                UserInteraction(
                    reelId = reelId,
                    watchedPercent = watchedPercent,
                    completedWatch = completedWatch,
                    liked = liked,
                    returnedToPrevious = returnedToPrevious,
                    lastWatchedAt = lastWatchedAt
                )
            } else {
                null
            }
        }
    }

    suspend fun updateWatchedPercent(reelId: String, percent: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey("watched_$reelId")] = percent
            preferences[longPreferencesKey("last_watched_$reelId")] = System.currentTimeMillis()
        }
    }

    suspend fun markCompletedWatch(reelId: String) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("completed_$reelId")] = true
            preferences[floatPreferencesKey("watched_$reelId")] = 1f
        }
    }

    suspend fun markLiked(reelId: String) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("liked_$reelId")] = true
        }
    }

    suspend fun markUnliked(reelId: String) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("liked_$reelId")] = false
        }
    }

    suspend fun markReturnedToPrevious(reelId: String) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("returned_$reelId")] = true
        }
    }

    suspend fun getHashtagInterestScores(): Flow<Map<String, Float>> {
        return dataStore.data.map { preferences ->
            val scores = mutableMapOf<String, Float>()
            // Extract all hashtag scores from preferences
            preferences.asMap().forEach { (key, value) ->
                if (key.name.startsWith("hashtag_") && value is Float) {
                    val hashtag = key.name.removePrefix("hashtag_")
                    scores[hashtag] = value
                }
            }
            scores
        }
    }

    suspend fun updateHashtagScore(hashtag: String, increment: Float) {
        dataStore.edit { preferences ->
            val currentScore = preferences[floatPreferencesKey("hashtag_$hashtag")] ?: 0f
            preferences[floatPreferencesKey("hashtag_$hashtag")] = currentScore + increment
        }
    }

    suspend fun getAllInteractions(): Flow<Map<String, UserInteraction>> {
        return dataStore.data.map { preferences ->
            val interactions = mutableMapOf<String, UserInteraction>()
            val reelIds = preferences.asMap().keys
                .mapNotNull { key ->
                    when {
                        key.name.startsWith("watched_") -> key.name.removePrefix("watched_")
                        key.name.startsWith("completed_") -> key.name.removePrefix("completed_")
                        key.name.startsWith("liked_") -> key.name.removePrefix("liked_")
                        key.name.startsWith("returned_") -> key.name.removePrefix("returned_")
                        else -> null
                    }
                }
                .toSet()

            reelIds.forEach { reelId ->
                val watchedPercent = preferences[floatPreferencesKey("watched_$reelId")] ?: 0f
                val completedWatch = preferences[booleanPreferencesKey("completed_$reelId")] ?: false
                val liked = preferences[booleanPreferencesKey("liked_$reelId")] ?: false
                val returnedToPrevious = preferences[booleanPreferencesKey("returned_$reelId")] ?: false
                val lastWatchedAt = preferences[longPreferencesKey("last_watched_$reelId")] ?: System.currentTimeMillis()

                if (watchedPercent > 0f || completedWatch || liked || returnedToPrevious) {
                    interactions[reelId] = UserInteraction(
                        reelId = reelId,
                        watchedPercent = watchedPercent,
                        completedWatch = completedWatch,
                        liked = liked,
                        returnedToPrevious = returnedToPrevious,
                        lastWatchedAt = lastWatchedAt
                    )
                }
            }
            interactions
        }
    }
}
