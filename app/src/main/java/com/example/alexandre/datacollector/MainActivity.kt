package com.example.alexandre.datacollector

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.alexandre.datacollector.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {//, CoroutineScope {

    companion object {

        var tvresult: EditText? = null
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}