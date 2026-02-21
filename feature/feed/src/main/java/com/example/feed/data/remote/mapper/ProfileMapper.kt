package com.example.feed.data.remote.mapper

import com.example.feed.data.remote.dto.ProfileDto
import com.example.feed.domain.model.AccountType
import com.example.feed.domain.model.Profile

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

