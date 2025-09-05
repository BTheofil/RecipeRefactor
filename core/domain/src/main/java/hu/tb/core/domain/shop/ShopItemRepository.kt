package hu.tb.core.domain.shop

import kotlinx.coroutines.flow.Flow

interface ShopItemRepository {
    suspend fun saveItem(item: ShopItem): Long
    suspend fun deleteItem(item: ShopItem)
    suspend fun addShoppingItemsToDepo(shopItems: List<ShopItem>)

    fun getAllItem(): Flow<List<ShopItem>>
}