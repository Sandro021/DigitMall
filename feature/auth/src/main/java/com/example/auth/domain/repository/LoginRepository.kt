package com.example.auth.domain.repository

import com.example.auth.domain.model.Profile

interface LoginRepository {
    suspend fun login(email: String, password: String): Profile
}