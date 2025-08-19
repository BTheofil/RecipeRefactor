package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.domain.shop.ShopItem
import hu.tb.core.domain.shop.ShopItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShopItemRepositoryImpl(
    private val dao: ShopDao,
    private val productDao: ProductDao,
) : ShopItemRepository {

    override suspend fun saveItem(item: ShopItem): Long =
        dao.insert(item.toEntity())

    override suspend fun deleteItem(item: ShopItem) =
        dao.delete(item.toEntity())

    override fun getAllItem(): Flow<List<ShopItem>> =
        dao.getAll().map { items ->
            items.map { it.toShopDomain() }
        }

    override suspend fun addShoppingItemsToDepo(shopItems: List<ShopItem>) {
        val currentProducts = productDao.getAll()

        shopItems.forEach { shopItem ->
            val matchingProduct = currentProducts.find { product ->
                product.name == shopItem.name &&
                        product.measure.category == shopItem.measure.category
            }

            if (matchingProduct != null) {
                val shopUnits = shopItem.quantity * shopItem.measure.factor
                val productUnits = matchingProduct.quantity * matchingProduct.measure.factor
                val newQuantity = (shopUnits + productUnits) / matchingProduct.measure.factor

                productDao.insert(matchingProduct.copy(quantity = newQuantity))
            } else {
                productDao.insert(
                    ProductEntity(
                        name = shopItem.name,
                        quantity = shopItem.quantity,
                        measure = shopItem.measure,
                    )
                )
            }
        }
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
