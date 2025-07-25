package hu.tb.core.domain.product

interface ProductRepository {

    suspend fun insert(product: Product): Long

    suspend fun delete(product: Product)

    suspend fun getAll(): List<Product>
}