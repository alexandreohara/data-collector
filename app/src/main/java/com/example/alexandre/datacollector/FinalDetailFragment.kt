package com.example.alexandre.datacollector

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.navigation.fragment.findNavController
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
import com.example.alexandre.datacollector.db.ItemDatabase
import com.example.alexandre.datacollector.item.ItemViewModel
import com.example.alexandre.datacollector.item.ItemViewModelFactory
import com.warkiz.widget.IndicatorSeekBar
import java.io.File
import java.io.FileWriter
import java.io.IOException
import android.widget.Toast
import kotlinx.android.synthetic.main.final_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    val REQUEST_TAKE_PHOTO = 1
    var currentPhotoPath: String = ""

    private lateinit var binding: FinalDetailBinding
    private lateinit var itemViewModel: ItemViewModel

    // TODO: numero de serie fake. Apagar
    private var serialNum = "1234"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.final_detail, container, false)

        //val dropDown = Array(100, { i -> (i + 1).toString() })

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
            //createCSV()
            //writeCSV()
            // Toast.makeText(context, binding.t3DropdownList.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            galleryAddPic()
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
            dispatchTakePictureIntent()
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            context!!.sendBroadcast(mediaScanIntent)
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        println(timeStamp)
        val storageDir: File =  activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println(storageDir)
        return File.createTempFile(
                // TODO: Recuperar o numero de serie e colocar na foto!
                "${serialNum}_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Toast.makeText(context, currentPhotoPath, Toast.LENGTH_SHORT).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            context!!,
                            "com.example.android.fileprovider",
                            it
                    )
                    println(photoURI)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    println('f')

                }
            }
        }
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
