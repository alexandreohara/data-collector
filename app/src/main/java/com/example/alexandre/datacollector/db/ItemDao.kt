package com.example.alexandre.datacollector.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/* Data Access Object - Classe para acessar as informacoes do banco de dados.
Usar @Query para adicionar uma Query customizada
 */

@Dao

interface ItemDao {
    @Insert
    fun insert(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteLine()

    @Query("SELECT * FROM item_table")
    fun getAllItems(): List<Item>

    @Query("SELECT * from item_table WHERE number = :key")
    fun getItem(key: String): Item?

    @Query("SELECT * FROM item_table WHERE serialNumber = :serialNumber")
    fun getItemBySerialNumber(serialNumber: String): Item?

    @Query("SELECT count(*) FROM item_table")
    fun getRowsCount(): Int

}
