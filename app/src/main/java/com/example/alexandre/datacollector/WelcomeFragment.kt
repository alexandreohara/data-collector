package com.example.alexandre.datacollector


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.WelcomeBinding
import com.example.alexandre.datacollector.db.Item
import com.example.alexandre.datacollector.db.ItemDatabase
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import com.opencsv.enums.CSVReaderNullFieldIndicator
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import kotlin.coroutines.CoroutineContext


/**
 * A simple [Fragment] subclass.
 *
 */
class WelcomeFragment : Fragment(), CoroutineScope {

    private lateinit var binding: WelcomeBinding

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.welcome, container, false)
        binding.addMainButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_welcomeFragment_to_newItemFragment)
        }
        binding.dbMainButton.setOnClickListener { view: View ->
            val application = requireNotNull(this.activity).application
            job = Job()
            launch {
                async(Dispatchers.Default) {
                    readCSV(application, "Export_Hardware.csv")
                    readCSV(application, "Export_Notebook.csv")
                    readCSV(application, "Export_Desktop.csv")
                    readCSV(application, "Export_Ramal.csv")
                }
            }
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Pede autorização para usar a camera se ainda não tinha
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun readCSV(application: Application, fileName: String) {

        var fileReader: BufferedReader? = null
        var csvReader: CSVReader? = null
        try {
            fileReader = activity?.applicationContext?.assets?.open(fileName)?.bufferedReader()
            csvReader = CSVReaderBuilder(fileReader).
                    withCSVParser(CSVParserBuilder().
                            withSeparator(';').
                            withIgnoreQuotations(false).
                            withFieldAsNull(CSVReaderNullFieldIndicator.BOTH).
                            build()).
                    withSkipLines(1).build()
            var line: Array<String>?
            line = csvReader.readNext()
            while (line != null) {
                var item = Item(
                        number = line[0],
                        name = line[1],
                        deploymentState = line[2],
                        incidentState = line[3],
                        vendor = line[4],
                        model = line[5],
                        description = line[6],
                        type = line[7],
                        owner = line[8],
                        serialNumber = line[9]
                )
                val dataSource = ItemDatabase.getInstance(application).itemDao()
                //println(dataSource.getRowsCount())
                //println(item)
                dataSource.insert(item)
                //PopulateDbAsync(item, application)
                line = csvReader.readNext()
            }
            //val dataSource = ItemDatabase.getInstance(application).itemDao()
            //println("Numero de itens no BD: " + dataSource.getRowsCount())
            csvReader.close()
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
                csvReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }

    }
}
