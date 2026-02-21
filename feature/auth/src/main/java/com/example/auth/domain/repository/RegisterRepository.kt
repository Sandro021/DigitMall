package com.example.auth.domain.repository

import com.example.auth.domain.model.AccountType

interface RegisterRepository {
    suspend fun register(
        accountType: AccountType,
        email: String,
        password: String,
        userFirstName: String?,
        userLastName: String?,
        phone: String?,
        companyName: String?,
        location: String?,
        description: String?
    )
}
