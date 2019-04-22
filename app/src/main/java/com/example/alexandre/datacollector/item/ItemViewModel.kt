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

    //var item = MutableLiveData<Item?>()
    var number: String = ""
    var name: String = ""
    var oldName = MutableLiveData<String>()
    var deploymentState: String? = ""
    var serialNumber: String? = ""
    var vendor: String? = ""
    var model: String? = ""
    var type: String? = ""
    var description: String? = ""
    var observation: String? = ""
    var qualityState: Int? = 0
    var localization: String? = ""

    var typeSelected = ""
    var doneNavigating = false

    fun onButtonClicked() {
        uiScope.launch {
            _navigateToDetails.value = getItemFromDatabase(typeSelected)

        }
    }

    //executa a acao de buscar no database em outra thread.
    private suspend fun getItemFromDatabase(typeSelected: String): Item? {
        return withContext(Dispatchers.IO) {
            //val rows = database.getRowsCount()
            var item: Item?
            var serialNumber = serialNumber ?: ""
            val name = oldName.value ?: ""
            if (typeSelected == "NUMBER") {
                item = database.getItem(name)
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
        doneNavigating = true
    }

    fun clearData() {
        number = ""
        name = ""
        oldName.value = ""
        deploymentState = ""
        serialNumber = ""
        vendor = ""
        model = ""
        type = ""
        description = ""
        localization = ""
        doneNavigating = false
    }

}