package com.example.alexandre.datacollector.item

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.alexandre.datacollector.db.ItemDao

/**
 * Created by alexandre on 04/04/19.
 */

class ItemViewModel(
        val database: ItemDao,
        application: Application) : AndroidViewModel(application) {
}