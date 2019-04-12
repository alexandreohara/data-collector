package com.example.alexandre.datacollector.item

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.alexandre.datacollector.db.Item
import com.example.alexandre.datacollector.db.ItemDao

/**
 * Created by alexandre on 04/04/19.
 */

class ItemViewModel(val database: ItemDao, application: Application) : AndroidViewModel(application) {

    private var number = 0
    private var serialNumber = ""
    private var item = MutableLiveData<Item?>()

    private val _navigateToDetails = MutableLiveData<Item>()
    val navigateToDetails: LiveData<Item>
        get() = _navigateToDetails

    fun getItemByNumber(number: Int) {
        database.getItem(number)
    }

    fun getItemBySerialNumber(serialNumber: String) {
        database.getItemBySerialNumber(serialNumber)
    }

    fun doneNavigating() {
        _navigateToDetails.value = null
    }
}