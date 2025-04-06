package hu.tb.domain

import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {

    suspend fun saveItem(item: ShoppingItem)

    fun deleteItem(item: ShoppingItem)

    fun getAllItem(): Flow<List<ShoppingItem>>
}