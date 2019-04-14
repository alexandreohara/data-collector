package com.example.alexandre.datacollector

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.ImageButton
import android.widget.Toast
import com.example.alexandre.datacollector.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val paths = arrayOf("item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3")
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}


