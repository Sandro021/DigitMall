package com.example.auth.domain.repository

import com.example.auth.domain.model.Profile
import com.example.auth.domain.model.Resource

interface LoginRepository {
    suspend fun login(email: String, password: String): Profile

    suspend fun getProfileById(id: String): Resource<Profile>
}