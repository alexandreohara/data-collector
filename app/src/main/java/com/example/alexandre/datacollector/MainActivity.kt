package com.example.alexandre.datacollector

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import com.warkiz.widget.IndicatorSeekBar
import kotlinx.android.synthetic.main.final_detail.*
import android.widget.ArrayAdapter
import com.example.alexandre.datacollector.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val paths = arrayOf("item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3")
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //setContentView(R.layout.activity_main)
        //setContentView(R.layout.add_new_item)
//        setContentView(R.layout.final_detail)
//
//        val adapter = ArrayAdapter(this@MainActivity,
//                android.R.layout.simple_spinner_item, paths)
//
//        var seekBar: IndicatorSeekBar = findViewById(R.id.seek_bar)
////        val arr = arrayOf("Péssimo", "Ruim", "Bom", "Ótimo")
////        seekBar.customTickTexts(arr)
//        seekBar.setIndicatorTextFormat("\${PROGRESS} %")
//
//        var spinner: Spinner = findViewById(R.id.spinner)
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter
//

    }
}



//import android.appcompat.app.AppCompatActivity

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//    }
//}
