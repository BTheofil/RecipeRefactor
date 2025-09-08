package hu.tb.core.domain.product

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insert(product: Product): Long
    suspend fun delete(product: Product)
    suspend fun getAll(): List<Product>
    fun getAllFlow(): Flow<List<Product>>
}