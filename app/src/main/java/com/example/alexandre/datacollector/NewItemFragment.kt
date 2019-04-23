package com.example.alexandre.datacollector


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.alexandre.datacollector.item.ItemViewModel
import com.example.alexandre.datacollector.item.ItemViewModelFactory
import com.example.alexandre.datacollector.databinding.AddNewItemBinding
import com.example.alexandre.datacollector.db.ItemDatabase

/**
 * A simple [Fragment] subclass.
 *
 */
class NewItemFragment : Fragment() {

    private lateinit var binding: AddNewItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_new_item, container, false)

        // referencia do application que este fragmento está ligado para passar pro ViewModelProvider
        val application = requireNotNull(this.activity).application
        val dataSource = ItemDatabase.getInstance(application).itemDao()
        val viewModelFactory = ItemViewModelFactory(dataSource, application)
        var itemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)
        }

        // validacao dos campos antes da navegacao
        binding.t1ContinueButton.setOnClickListener {
            if (binding.t1ScanRadio.isChecked && binding.t1ScanText.text.toString().trim() == "") {
                Toast.makeText(context, "Código de Barras é obrigatório!", Toast.LENGTH_SHORT).show()
            } else if (binding.t1SerialRadio.isChecked && binding.t1SerialText.text.toString().trim() == ""){
                Toast.makeText(context, "Serial Number é obrigatório!", Toast.LENGTH_SHORT).show()
            }else if (binding.t1RadioGroup.checkedRadioButtonId == -1){
                Toast.makeText(context, "Selecione uma das opções!", Toast.LENGTH_SHORT).show()
            } else {
                binding.t1ContinueButton.text = "Aguarde..."
                println(itemViewModel?.oldName?.value)
                itemViewModel?.onButtonClicked()
            }
        }

        // listeners para mostrar/esconder campos de texto
        binding.t1ScanRadio.setOnClickListener {
            binding.t1ScanText.visibility = View.VISIBLE
            binding.t1SerialText.visibility = View.GONE
            itemViewModel?.typeSelected = "NUMBER"
        }

        binding.t1SerialRadio.setOnClickListener {
            binding.t1SerialText.visibility = View.VISIBLE
            binding.t1ScanText.visibility = View.GONE
            itemViewModel?.typeSelected = "SERIAL_NUMBER"
        }

        binding.t1ManualRadio.setOnClickListener {
            binding.t1SerialText.visibility = View.GONE
            binding.t1ScanText.visibility = View.GONE
            itemViewModel?.typeSelected = "MANUAL"
        }

        binding.itemViewModel = itemViewModel
        binding.setLifecycleOwner(this.activity)

        itemViewModel?.navigateToDetails?.observe(this, Observer {
            item ->
            if (item == null) {
                if (itemViewModel.doneNavigating == false) {
                    Toast.makeText(context, "Item não encontrado!", Toast.LENGTH_SHORT).show()
                    binding.t1ContinueButton.text = "Continuar"
                }
            } else {
                itemViewModel.description = item.description
                itemViewModel.number = item.number
                itemViewModel.model = item.model
                itemViewModel.oldName.value = item.name
                itemViewModel.deploymentState = item.deploymentState
                itemViewModel.serialNumber = item.serialNumber
                itemViewModel.vendor = item.vendor
                itemViewModel.model = item.model
                itemViewModel.type = item.type
                itemViewModel.description = item.description

                findNavController().navigate(R.id.action_newItemFragment_to_detailsFragment2)
                itemViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.t1ContinueButton.text = "CONTINUAR"

        // mostra o campo de texto do Radio Button selecionado
        if (findSelected() == "NUMBER") {
            binding.t1ScanText.visibility = View.VISIBLE
        } else if (findSelected() == "SERIAL_NUMBER") {
            binding.t1SerialText.visibility = View.VISIBLE
        }
    }

    private fun findSelected(): String {
        if (binding.t1RadioGroup.checkedRadioButtonId == binding.t1ScanRadio.id) {
            return "NUMBER"
        } else if (binding.t1RadioGroup.checkedRadioButtonId == binding.t1SerialRadio.id) {
            return "SERIAL_NUMBER"
        } else{
            return "MANUAL"
        }
    }
}
