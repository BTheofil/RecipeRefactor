package hu.tb.database.repository

import hu.tb.database.dao.ShoppingDao
import hu.tb.database.mapper.toDomain
import hu.tb.database.mapper.toEntity
import hu.tb.domain.ShoppingItem
import hu.tb.domain.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingRepositoryImpl(
    private val dao: ShoppingDao
) : ShoppingRepository {

    override suspend fun saveItem(item: ShoppingItem) =
        dao.insert(item.toEntity())

    override fun deleteItem(item: ShoppingItem) =
        dao.delete(item.toEntity())

    override fun getAllItem(): Flow<List<ShoppingItem>> =
        dao.getAll().map { items ->
            items.map { it.toDomain() }
        }
}