package com.example.company_profile.presentation.contract

sealed class CompanyEffect {
    data class ShowToast(val message: String) : CompanyEffect()
}