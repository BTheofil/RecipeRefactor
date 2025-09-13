package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.entity.toDomain
import hu.tb.core.data.database.entity.toEntity
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun insert(product: Product): Long =
        dao.insert(product.toEntity())

    override suspend fun delete(product: Product) =
        dao.delete(product.toEntity())

    override fun getAllFlow(): Flow<List<Product>> =
        dao.getAllFlow().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getProductById(id: Long): Product? =
        dao.getProductById(id)?.toDomain()
}