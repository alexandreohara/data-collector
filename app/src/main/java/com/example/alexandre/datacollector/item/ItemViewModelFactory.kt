package com.example.alexandre.datacollector.item

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.alexandre.datacollector.db.ItemDao
import java.lang.IllegalArgumentException

class ItemViewModelFactory(
        private val dataSource: ItemDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}