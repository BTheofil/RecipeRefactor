package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.ProductEntity
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
            items.map { it.toShopDomain() }
        }
}

private fun ShopItem.toEntity(): ProductEntity = ProductEntity(
    productId = id,
    name = name,
    quantity = quantity,
    measure = measure,
    isChecked = isChecked,
)

private fun ProductEntity.toShopDomain(): ShopItem = ShopItem(
    id = productId,
    name = name,
    quantity = quantity,
    measure = measure,
    isChecked = isChecked ?: false
)
