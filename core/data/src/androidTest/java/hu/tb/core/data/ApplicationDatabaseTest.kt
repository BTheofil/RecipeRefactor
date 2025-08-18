package hu.tb.core.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import hu.tb.core.data.database.ApplicationDatabase
import hu.tb.core.data.database.dao.ProductDao
import hu.tb.core.data.database.dao.RecipeDao
import hu.tb.core.data.database.dao.ShopDao
import hu.tb.core.data.database.entity.ProductEntity
import hu.tb.core.domain.product.Measure
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.io.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var shopDao: ShopDao
    private lateinit var productDao: ProductDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ApplicationDatabase::class.java
        ).build()
        db.also {
            recipeDao = it.recipeDao()
            shopDao = it.shopDao()
            productDao = it.productDao()
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun shopDao_CRUD() = runBlocking {
        val mockShopItem = ProductEntity(
            name = "first",
            isChecked = false,
            quantity = 1,
            measure = Measure.PIECE
        )

        shopDao.insert(mockShopItem)

        val item = shopDao.getAll().first().find { it.productId == 1L }
        assertThat(item!!.name).isEqualTo(mockShopItem.name)
        assertThat(item.isChecked).isFalse()

        val mockShopItem2 = ProductEntity(
            name = "second",
            isChecked = true,
            quantity = 1,
            measure = Measure.PIECE
        )

        shopDao.insert(mockShopItem2)

        val items = shopDao.getAll().first()
        assertThat(items).hasSize(2)

        val item2 = items.find { it.productId == 1L }
        assertThat(item2!!.name).matches(mockShopItem.name)
        assertThat(item2.isChecked).isFalse()

        val firstEntity = shopDao.getAll().first().first()
        shopDao.delete(firstEntity)
        val remainingItems = shopDao.getAll().first()
        assertThat(remainingItems).hasSize(1)

        val updateItem2 = shopDao.getAll().first().first().copy(
            name = "New_Second",
            isChecked = false
        )
        shopDao.insert(updateItem2)
        assertThat(shopDao.getAll().first().first().name).isEqualTo(updateItem2.name)
        assertThat(shopDao.getAll().first().first().isChecked).isFalse()
    }
}