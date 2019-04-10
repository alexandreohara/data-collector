package com.example.alexandre.datacollector


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: AddNewItemBinding = DataBindingUtil.inflate(inflater, R.layout.add_new_item, container, false)
        binding.t1ContinueButton.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_newItemFragment_to_detailsFragment2)
        }

        binding.t1ScanRadio.setOnClickListener {
            binding.t1ScanText.visibility = View.VISIBLE
            binding.t1SerialText.visibility = View.GONE
        }

        binding.t1SerialRadio.setOnClickListener {
            binding.t1SerialText.visibility = View.VISIBLE
            binding.t1ScanText.visibility = View.GONE
        }

        // referencia do application que este fragmento está ligado para passar pro ViewModelProvider
        val application = requireNotNull(this.activity).application
        val dataSource = ItemDatabase.getInstance(application).itemDao()
        val viewModelFactory = ItemViewModelFactory(dataSource, application)
        val itemViewModel = ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel::class.java)

        binding.itemViewModel = itemViewModel
        binding.setLifecycleOwner(this)

        itemViewModel.navigateToDetails.observe(this, Observer {
            item ->
            item?.let {
                this.findNavController().navigate(R.id.action_newItemFragment_to_detailsFragment2)
            }
        })

        return binding.root
    }


}
