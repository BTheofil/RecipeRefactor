package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.toDomain
import hu.tb.core.data.database.entity.toEntity
import hu.tb.core.domain.shop.ShopItem
import hu.tb.core.domain.shop.ShopItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShopItemRepositoryImpl(
    private val dao: ShopDao
) : ShopItemRepository {

    override suspend fun saveItem(item: ShopItem): Long =
        dao.insert(item.toEntity())

    override suspend fun deleteItem(item: ShopItem) =
        dao.delete(item.toEntity())

    override fun getAllItem(): Flow<List<ShopItem>> =
        dao.getAll().map { items ->
            items.map { it.toDomain() }
        }
}