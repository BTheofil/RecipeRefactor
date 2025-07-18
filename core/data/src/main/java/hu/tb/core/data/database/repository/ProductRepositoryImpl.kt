package hu.tb.core.data.database.repository

import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.entity.toDomain
import hu.tb.core.data.database.entity.toEntity
import hu.tb.core.domain.product.Product
import hu.tb.core.domain.product.ProductRepository

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun insert(product: Product) =
        dao.insert(product.toEntity())

    override suspend fun delete(product: Product) =
        dao.delete(product.toEntity())

    override suspend fun getAll(): List<Product> =
        dao.getAll().map { food -> food.toDomain() }
}