package com.example.company_profile.data.repository.shop


import androidx.core.net.toUri
import com.example.company_profile.data.mapper.toDomain
import com.example.company_profile.data.mapper.toDto
import com.example.company_profile.data.remote.shop.CompanyShopApiService
import com.example.company_profile.domain.model.shop.Shop
import com.example.company_profile.domain.model.shop.ShopItem
import com.example.company_profile.domain.repository.shop.ShopRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: CompanyShopApiService,
    private val firebaseStorage: FirebaseStorage
) : ShopRepository {

    override suspend fun createShop(shop: Shop, localImageUri: String): Result<Shop> {
        return runCatching {
            withContext(Dispatchers.IO) {

                val imageUri = localImageUri.toUri()
                val fileName = "shops/cover_${System.currentTimeMillis()}.jpg"
                val storageRef = firebaseStorage.reference.child(fileName)

                storageRef.putFile(imageUri).await()
                val downloadUrl = storageRef.downloadUrl.await().toString()

                val shopWithImage = shop.copy(imageUrl = downloadUrl)
                api.createShop(shopWithImage.toDto()).toDomain()
            }
        }
    }

    override suspend fun getShops(companyId: String): Result<List<Shop>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                api.getShopsByCompany(companyId).map { it.toDomain() }
            }
        }
    }

    override suspend fun createItem(item: ShopItem, localImageUri: String): Result<ShopItem> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val imageUri = localImageUri.toUri()
                val fileName = "shop_items/item_${System.currentTimeMillis()}.jpg"
                val storageRef = firebaseStorage.reference.child(fileName)

                storageRef.putFile(imageUri).await()
                val downloadUrl = storageRef.downloadUrl.await().toString()

                val itemWithImage = item.copy(imageUrl = downloadUrl)

                api.createItem(itemWithImage.toDto()).toDomain()
            }
        }
    }

    override suspend fun getItems(shopId: String): Result<List<ShopItem>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                api.getItemsByShop(shopId).map { it.toDomain() }
            }
        }
    }
}