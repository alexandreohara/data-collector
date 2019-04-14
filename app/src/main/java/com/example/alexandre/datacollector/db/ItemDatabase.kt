package com.example.alexandre.datacollector.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import android.arch.persistence.room.Room

/* Classe que junta a entidade Item com o DAO */

@Database(entities = arrayOf(Item::class), version = 1)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            ItemDatabase::class.java,
                            "items_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build()
                    INSTANCE = instance
                }

                return instance
            }

        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE)
                        .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ItemDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val itemDao = db?.itemDao()

        override fun doInBackground(vararg p0: Unit?) {
            itemDao?.insert(Item(
                    number = "1234",
                    name = "name",
                    deploymentState = "deploymentState",
                    description = "description",
                    incidentState = "incidentState",
                    vendor = "SAMSUNG",
                    model = "MODEL",
                    type = "type",
                    owner = "Owner",
                    serialNumber = "SERIAL_NUMBER",
                    operatingSystem = "operatingSystem",
                    graphicAdapter = "graphicAdapter",
                    otherEquipment = "otherEquipment",
                    warrantyExpirationDate = "warrantyExpirationDate",
                    installDate = "installDate",
                    note = "note"

            ))
            //noteDao?.insert(Item("Title 1", "description 1"))
            //noteDao?.insert(Item("Title 2", "description 2"))
            //noteDao?.insert(Item("Title 3", "description 3"))
        }
    }

}