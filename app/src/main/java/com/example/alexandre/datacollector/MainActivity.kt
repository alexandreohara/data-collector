package com.example.alexandre.datacollector

import android.app.Application
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.ImageButton
import android.widget.Toast
import com.example.alexandre.datacollector.databinding.ActivityMainBinding
import com.example.alexandre.datacollector.db.Item
import com.example.alexandre.datacollector.db.ItemDatabase
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import com.opencsv.enums.CSVReaderNullFieldIndicator
import kotlinx.coroutines.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {//, CoroutineScope {

    private val paths = arrayOf("item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3","item 1", "item 2", "item 3")
    private lateinit var binding: ActivityMainBinding
//    private lateinit var job: Job
//    override val coroutineContext: CoroutineContext
//        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        val application = requireNotNull(this).application
//        job = Job()
//        launch {
//            async(Dispatchers.Default) {
//                readCSV(application)
//            }
//        }


    }

//    override fun onDestroy() {
//        super.onDestroy()
//        job.cancel()
//    }


//    private fun readCSV(application: Application) {
//
//        var fileReader: BufferedReader? = null
//        var csvReader: CSVReader? = null
//        try {
//            fileReader = applicationContext.assets.open("Export_Ramal.csv").bufferedReader()
//            csvReader = CSVReaderBuilder(fileReader).
//                    withCSVParser(CSVParserBuilder().
//                            withSeparator(';').
//                            withIgnoreQuotations(false).
//                            withFieldAsNull(CSVReaderNullFieldIndicator.BOTH).
//                            build()).
//                    withSkipLines(1).build()
//            var line: Array<String>?
//            line = csvReader.readNext()
//            while (line != null) {
//                var item = Item(
//                        number = line[0],
//                        name = line[1],
//                        deploymentState = line[2],
//                        incidentState = line[3],
//                        vendor = line[4],
//                        model = line[5],
//                        description = line[6],
//                        type = line[7],
//                        owner = line[8],
//                        serialNumber = line[9]
//                )
//                val dataSource = ItemDatabase.getInstance(application).itemDao()
//                println(dataSource.getRowsCount())
//                //println(item)
//                dataSource.insert(item)
//                //PopulateDbAsync(item, application)
//                line = csvReader.readNext()
//            }
//            csvReader.close()
//        } catch (e: Exception) {
//            println("Reading CSV Error!")
//            e.printStackTrace()
//        } finally {
//            try {
//                fileReader!!.close()
//                csvReader!!.close()
//            } catch (e: IOException) {
//                println("Closing fileReader/csvParser Error!")
//                e.printStackTrace()
//            }
//        }

//    }



}

//class PopulateDbAsync(item: Item, application: Application) : AsyncTask<Any, Any, Any>() {
//    val application: Application
//    var item: Item
//    init {
//        this.application = application
//        this.item = item
//    }
//
//    override fun doInBackground(vararg p0: Any?): Any {
//        val dataSource = ItemDatabase.getInstance(application).itemDao()
//        dataSource.insert(item)
//        return 1
//    }
//}


