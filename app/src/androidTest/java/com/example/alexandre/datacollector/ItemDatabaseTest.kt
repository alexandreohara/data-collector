package com.example.alexandre.datacollector
import android.arch.persistence.room.Room
import android.support.test.runner.AndroidJUnit4
import android.support.test.InstrumentationRegistry
import com.example.alexandre.datacollector.db.ItemDatabase
import com.example.alexandre.datacollector.db.ItemDao
import com.example.alexandre.datacollector.db.Item
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
/**
 * Created by alexandre on 06/04/19.
 */



/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class ItemDatabaseTest {
    private lateinit var itemDao: ItemDao
    private lateinit var db: ItemDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        itemDao = db.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetItem() {
        val item = Item(
                number = 2313,
                name = "name",
                deploymentState = "deploymentState",
                description = "description",
                incidentState = "incidentState",
                vendor = "SAMSUNG",
                model = "MODEL",
                type = "type",
                owner = "Owner",
                serialNumber = "",
                operatingSystem = "",
                graphicAdapter = "",
                otherEquipment = "",
                warrantyExpirationDate = "",
                installDate = "",
                note = ""
        )
        itemDao.insert(item)
        val dbItem = itemDao.getItem(2313)
        assertEquals(dbItem?.name, "name")
    }
}
