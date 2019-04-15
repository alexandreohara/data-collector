package com.example.alexandre.datacollector


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.DetailsBinding
import com.example.alexandre.datacollector.db.ItemDatabase
import com.example.alexandre.datacollector.item.ItemViewModel
import com.example.alexandre.datacollector.item.ItemViewModelFactory


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsBinding

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details, container, false)
        binding.t2ContinueButton.setOnClickListener { view ->
            if (binding.t2NewNumberText.text.toString().trim() == "") {
                Toast.makeText(context, "Insira um novo número!", Toast.LENGTH_SHORT).show()
            } else {
                binding.t2ContinueButton.text = "Aguarde..."
                view.findNavController().navigate(R.id.action_detailsFragment2_to_finalDetailFragment)
            }
        }

        val application = requireNotNull(this.activity).application
        val dataSource = ItemDatabase.getInstance(application).itemDao()
        val viewModelFactory = ItemViewModelFactory(dataSource, application)
        var itemViewModel = activity?.run{
            ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)
        }

        binding.itemViewModel = itemViewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.t2ContinueButton.text = "CONTINUAR"
    }

}
