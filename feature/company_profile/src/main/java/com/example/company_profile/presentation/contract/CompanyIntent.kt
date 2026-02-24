package com.example.company_profile.presentation.contract

sealed class CompanyIntent {
    object LoadCompanyData : CompanyIntent()

    data class CreateShop(
        val name: String,
        val description: String,
        val location: String,
        val imageUri: String
    ) : CompanyIntent()

    data class UploadReel(val videoUri: String, val caption: String) : CompanyIntent()
    data class AddShopItem(
        val shopId: String,
        val name: String,
        val price: String,
        val sizes: List<String>,
        val category: String,
        val imageUri: String
    ) : CompanyIntent()


}
