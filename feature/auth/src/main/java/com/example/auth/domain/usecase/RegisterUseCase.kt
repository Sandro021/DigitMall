package com.example.auth.domain.usecase

import com.example.auth.domain.model.AccountType
import com.example.auth.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    suspend operator fun invoke(
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
        repo.register(
            accountType = accountType,
            email = email,
            password = password,
            userFirstName = userFirstName,
            userLastName = userLastName,
            phone = phone,
            companyName = companyName,
            location = location,
            description = description
        )
    }
}