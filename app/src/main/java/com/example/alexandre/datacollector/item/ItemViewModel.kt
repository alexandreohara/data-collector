package com.example.alexandre.datacollector.Item

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

/**
 * Created by alexandre on 04/04/19.
 */

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ItemRepository = ItemRepository(application)
}