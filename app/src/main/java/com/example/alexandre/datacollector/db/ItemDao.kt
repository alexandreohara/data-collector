package com.example.alexandre.datacollector.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao

interface ItemDao {
    @Insert
    fun insert(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteLine()

    @Query("SELECT * FROM item_table")
    fun getAllItems()
}