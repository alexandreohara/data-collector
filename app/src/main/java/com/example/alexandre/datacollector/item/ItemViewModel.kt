package com.example.alexandre.datacollector.item

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.alexandre.datacollector.db.Item
import com.example.alexandre.datacollector.db.ItemDao
import kotlinx.coroutines.*

/**
 * Created by alexandre on 04/04/19.
 */

class ItemViewModel(val database: ItemDao, application: Application) : AndroidViewModel(application) {

    // variaveis e metodos para o coroutine
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var item = MutableLiveData<Item?>()
    var number = "teste"
    var serialNumber = ""

    init {
        item.value = Item()
    }

    fun onButtonClicked(typeSelected: String) {
        uiScope.launch {
            item.value = getItemFromDatabase(typeSelected)
            _navigateToDetails.value = getItemFromDatabase(typeSelected)
        }
    }

    //executa a acao de buscar no database em outra thread.
    private suspend fun getItemFromDatabase(typeSelected: String): Item? {
        return withContext(Dispatchers.IO) {
            var item: Item?
            if (typeSelected == "NUMBER") {
                item = database.getItem(number)
            } else if (typeSelected == "SERIAL_NUMBER") {
                item = database.getItemBySerialNumber(serialNumber)
            } else {
                item = null
            }
            item
        }
    }

    private val _navigateToDetails = MutableLiveData<Item>()
    val navigateToDetails: LiveData<Item>
        get() = _navigateToDetails


    fun doneNavigating() {
        _navigateToDetails.value = null
    }
}