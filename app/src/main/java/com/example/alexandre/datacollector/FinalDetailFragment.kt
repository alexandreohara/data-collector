package com.example.alexandre.datacollector

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
import com.example.alexandre.datacollector.db.ItemDatabase
import com.example.alexandre.datacollector.item.ItemViewModel
import com.example.alexandre.datacollector.item.ItemViewModelFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    private var photoFile: File? = null
    private val CAPTURE_IMAGE_REQUEST = 1
    private val IMAGE_DIRECTORY_NAME = "Itens"
    private lateinit var binding: FinalDetailBinding
    private lateinit var itemViewModel: ItemViewModel

    // TODO: numero de serie fake. Apagar
    private var serialNum = "1234"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.final_detail, container, false)

        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Seu item foi registrado com sucesso!")
        dialog.setPositiveButton("Finalizar") { dialog, which->
            navigateHome()
        }
        dialog.setNegativeButton("Adicionar novo item") { dialog, which ->
            navigateNewItem()
        }

        binding.t3FinishButton.setOnClickListener { view ->
            binding.t3FinishButton.text = "Aguarde..."
            itemViewModel.qualityState = binding.seekBar.progress
            itemViewModel.localization = binding.t3DropdownList.selectedItem.toString()
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

    override fun onStart() {
        super.onStart()
        val dropDown = Array(100, { i -> "localização " + (i + 1).toString() })
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dropDown)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.t3DropdownList.adapter = adapter
        binding.t3CameraButton.setOnClickListener {
            captureImage()
        }
    }

    private fun createImageFile(): File? {
        // External sdcard location
        val mediaStorageDir = File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())

        var photoPrefixName: String

        if (itemViewModel.number != null) {
            photoPrefixName = itemViewModel.number
        } else {
            photoPrefixName = "IMG"
        }

        return File(mediaStorageDir.path + File.separator
                + photoPrefixName + "_" + timeStamp + ".jpg")

    }

    private fun captureImage() {

        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = createImageFile()
            if (photoFile != null) {
                val photoURI = Uri.fromFile(photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show()
        }

    }

    private fun navigateHome() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
    }

    private fun navigateNewItem() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
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
