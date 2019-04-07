package com.example.alexandre.datacollector.item

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.alexandre.datacollector.db.Item
import com.example.alexandre.datacollector.db.ItemDao

/**
 * Created by alexandre on 04/04/19.
 */

class ItemViewModel(val database: ItemDao, application: Application) : AndroidViewModel(application) {

    private var number = 0
    private var serialNumber = ""
    val item = Item(
            number = 0,
            name = "",
            deploymentState = "",
            description = "",
            incidentState = "",
            vendor = "",
            model = "",
            type = "",
            owner = "",
            serialNumber = "",
            operatingSystem = "",
            graphicAdapter = "",
            otherEquipment = "",
            warrantyExpirationDate = "",
            installDate = "",
            note = ""
    )

    fun getItemByNumber(number: Int) {
        database.getItem(number)
    }

    fun getItemBySerialNumber(serialNumber: String) {
        database.getItemBySerialNumber(serialNumber)
    }
}