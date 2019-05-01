package com.example.alexandre.datacollector

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest
import android.content.pm.PackageManager

/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    private var localization: Int = 0
    private var photoFile: File? = null
    private val CAPTURE_IMAGE_REQUEST = 1
    private val IMAGE_DIRECTORY_NAME = "Itens"
    private lateinit var mCurrentPhotoPath: String
    private lateinit var binding: FinalDetailBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.final_detail, container, false)



        val application = requireNotNull(this.activity).application
        val dataSource = ItemDatabase.getInstance(application).itemDao()
        val viewModelFactory = ItemViewModelFactory(dataSource, application)
        itemViewModel = activity!!.run{
            ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)
        }

        val dialog = createFinalDialog()

        binding.t3FinishButton.setOnClickListener {
            //            binding.t3FinishButton.text = "Aguarde..."
            itemViewModel.qualityState = binding.seekBar.progress
            itemViewModel.localization = binding.t3DropdownList.selectedItem.toString()
            itemViewModel.observation = binding.t3ObservationText.text.toString()
            val confirmationDialog = createDialogConfirmation(dialog)
            confirmationDialog.show()
        }

        binding.itemViewModel = itemViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dropDown = Array(100) { i -> "Localização " + (i + 1).toString() }
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dropDown)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.t3DropdownList.adapter = adapter
        binding.t3CameraButton.setOnClickListener {
            localization = binding.t3DropdownList.selectedItemPosition
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                captureImage()
            } else {
                captureImage2()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.t3DropdownList.setSelection(localization)
    }

    private fun createDialogConfirmation(dialog: AlertDialog.Builder): AlertDialog.Builder {
        val confirmationDialog = AlertDialog.Builder(context)
        confirmationDialog.setTitle("Confirme os dados preenchidos:")
        val str = "Placa Antiga: " + itemViewModel.oldName.value +
                "\nNova Placa: " + itemViewModel.name +
                "\nNúmero de Série: " + itemViewModel.serialNumber +
                "\nFornecedor: " + itemViewModel.vendor +
                "\nModelo: " + itemViewModel.model +
                "\nTipo: " + itemViewModel.type +
                "\nDescrição: " + itemViewModel.description +
                "\nEstado: " + binding.seekBar.progress +
                "\n" + binding.t3DropdownList.selectedItem.toString() +
                "\nObservações: " + binding.t3ObservationText.text.toString()

        confirmationDialog.setMessage(str)
        confirmationDialog.setNegativeButton("Cancelar") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        confirmationDialog.setPositiveButton("Ok") { _, _ ->
            binding.t3FinishButton.text = "Aguarde..."
            dialog.show()
        }
        return confirmationDialog
    }

    private fun createFinalDialog(): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Seu item foi registrado com sucesso!")
        dialog.setPositiveButton("Finalizar") { _: DialogInterface, _->
            createCSV()
            writeCSV()
            navigateHome()
        }
        dialog.setNeutralButton("Adicionar novo item") { _, _ ->
            createCSV()
            writeCSV()
            navigateNewItem()
        }
        return dialog
    }

    private fun createImageFile2(): File? {
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

        if (itemViewModel.name.trim() != "") {
            photoPrefixName = itemViewModel.name
        } else {
            photoPrefixName = "IMG"
        }

        return File(mediaStorageDir.path + File.separator
                + photoPrefixName + "_" + timeStamp + ".jpg")

    }

    private fun captureImage2() {

        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = createImageFile2()
            if (photoFile != null) {
                val photoURI = Uri.fromFile(photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                activity?.run {
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show()
        }

    }

    private fun captureImage() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
                try {
                    photoFile = createImageFile()
                    if (photoFile != null) {
                        var photoURI = FileProvider.getUriForFile(context!!,
                                "com.example.android.fileprovider",
                                photoFile!!)

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)

                    }
                } catch (ex: Exception) {
                    Toast.makeText(context, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(context, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var photoPrefixName: String

        if (itemViewModel.name.trim() != "") {
            photoPrefixName = itemViewModel.name
        } else {
            photoPrefixName = "IMG"
        }
        val imageFileName = photoPrefixName + "_" + timeStamp + "_"

        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage()
            }
        }
    }


    private fun navigateHome() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
    }

    private fun navigateNewItem() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
    }

    private fun createCSV() {
        val CSV_HEADER = "\"Name\",\"Old Name\",\"Deployment State\",\"Incident State\",\"Vendor\",\"Model\",\"Description\",\"Type\",\"Owner\",\"Serial Number\",\"Location\",\"Observation\""
        var dir = Environment.getExternalStorageDirectory()
        var file = File(dir, "/teste_3.csv")
        print(file.exists())
        if (!file.exists()) {
            var fileOutputStream = FileOutputStream(file, true)
            var fileWriter = OutputStreamWriter(fileOutputStream, "UTF-8")
            try {
                fileWriter.append(CSV_HEADER)
                fileWriter.append('\n')
            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter.flush()
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
        var file = File(dir, "/teste_3.csv")
        var fileOutputStream = FileOutputStream(file, true)
        var fileWriter = BufferedWriter(OutputStreamWriter(fileOutputStream, "UTF8"))
        try {
            fileWriter.append("\"" + itemViewModel.name + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.oldName + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.deploymentState + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.qualityState.toString() + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.vendor + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.model + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.description + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.type + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.serialNumber + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.localization + "\"")
            fileWriter.append(",")
            fileWriter.append("\"" + itemViewModel.observation + "\"")
            fileWriter.append('\n')

            fileWriter.close()

            println("Write CSV successfully!")
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

}