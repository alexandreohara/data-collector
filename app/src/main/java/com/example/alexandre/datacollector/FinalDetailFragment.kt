package com.example.alexandre.datacollector

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
import com.example.alexandre.datacollector.db.ItemDatabase
import com.example.alexandre.datacollector.item.ItemViewModel
import com.example.alexandre.datacollector.item.ItemViewModelFactory
import com.warkiz.widget.IndicatorSeekBar
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    private lateinit var binding: FinalDetailBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.final_detail, container, false)
        var seekBar: IndicatorSeekBar? = view?.findViewById(R.id.seek_bar)


        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Seu item foi registrado com sucesso!")
        dialog.setPositiveButton("Finalizar") { dialog, which->
            navigateHome()
        }
        dialog.setNegativeButton("Adicionar novo item") { dialog, which ->
            navigateNewItem()
        }

        binding.t3FinishButton.setOnClickListener { view ->
            binding.t3FinishButton.text = "Aguarde!"
            itemViewModel.qualityState = seekBar?.progress
            createCSV()
            writeCSV()
            dialog.show()
        }

        val application = requireNotNull(this.activity).application
        val dataSource = ItemDatabase.getInstance(application).itemDao()
        val viewModelFactory = ItemViewModelFactory(dataSource, application)
        itemViewModel = activity!!.run{
            ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)
        }

        binding.itemViewModel = itemViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun navigateHome() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
        println(itemViewModel.qualityState)
    }

    private fun navigateNewItem() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
        println(itemViewModel.qualityState)
    }

    private fun createCSV() {
        val CSV_HEADER = "id,name"
        var dir = Environment.getExternalStorageDirectory()
        var file: File = File(dir, "/teste4.csv")
        print(file.exists())
        if (!file.exists()) {
            var fileWriter = FileWriter(file)
            try {
                fileWriter.append(CSV_HEADER)
                fileWriter.append('\n')
            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }
        }
    }

    private fun writeCSV() {

        // var fileWriter: FileWriter? = null
        var dir = Environment.getExternalStorageDirectory()
        var file: File = File(dir, "/teste4.csv")
        var fileWriter = FileWriter(file, true)
        var random = Random()
        var int: Int = random.nextInt()
        try {
            fileWriter.append("teste")
            fileWriter.append(",")
            fileWriter.append(int.toString())
            fileWriter.append('\n')

            fileWriter.close()

            //val os = FileOutputStream()
            //os.write(data)

            println("Write CSV successfully!")
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

}
