package com.example.alexandre.datacollector

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.alexandre.datacollector.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val paths = arrayOf("item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3")
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}


