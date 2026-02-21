package com.example.auth.domain.model

data class Profile(
    val id: String,
    val accountType: AccountType,

    val username: String?,
    val companyName: String?,
    val firstName: String?,
    val lastName: String?,

    val avatarUrl: String?,
    val description: String?,
    val phone: String?,
    val location: String?
) {
    val displayName: String =
        companyName?.takeIf { it.isNotBlank() }
            ?: listOfNotNull(firstName?.takeIf { it.isNotBlank() }, lastName?.takeIf { it.isNotBlank() })
                .joinToString(" ")
                .takeIf { it.isNotBlank() }
            ?: username?.takeIf { it.isNotBlank() }
            ?: id
}
