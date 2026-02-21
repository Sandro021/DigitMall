package com.example.auth.data.remote.mapper

import com.example.auth.data.remote.dto.ProfileDto
import com.example.auth.domain.model.AccountType
import com.example.auth.domain.model.Profile


fun ProfileDto.toDomain(): Profile {
    val type = when (accountType.uppercase()) {
        "BUSINESS" -> AccountType.BUSINESS
        else -> AccountType.USER
    }

    return Profile(
        id = id,
        accountType = type,
        username = username,
        companyName = companyName,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        description = description,
        phone = phone,
        location = location
    )
}