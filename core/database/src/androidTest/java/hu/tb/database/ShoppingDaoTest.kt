package hu.tb.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import hu.tb.database.dao.ShoppingDao
import hu.tb.database.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {
    private lateinit var shoppingDao: ShoppingDao
    private lateinit var shoppingDb: ShoppingDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        shoppingDb = Room.inMemoryDatabaseBuilder(
            context, ShoppingDatabase::class.java
        ).build()
        shoppingDao = shoppingDb.shoppingDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        shoppingDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemAndRetrieve() = runBlocking {
        val mockItem = ShoppingItemEntity(
            name = "item",
            isChecked = false
        )

        shoppingDao.insert(mockItem)
        var items = shoppingDao.getAll().first()
        assertThat(items.first().name, `is`("item"))

        items = shoppingDao.getAll().first() //for updating the current list
        shoppingDao.insert(items.first().copy(name = "other"))

        items = shoppingDao.getAll().first()
        assertThat(items.size, `is`(1))
        assertThat(items.first().name, `is`("other"))

        shoppingDao.delete(items.first())
        items = shoppingDao.getAll().first()
        assertThat(items, `is`(emptyList()))
    }
}