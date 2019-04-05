package com.example.alexandre.datacollector.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "item_table")

data class Item(
        var name: String,
        var deploymentState: String,
        var incidentState: String,
        var vendor: String,
        var model: String,
        var description: String,
        var type: String,
        var owner: String,
        var serialNumber: String,
        var operatingSystem: String,
        var graphicAdapter: String,
        var otherEquipment: String,
        var warrantyExpirationDate: String,
        var installDate: String,
        var note: String
) {
    @PrimaryKey(autoGenerate = false)
    var number: Int = 0
}