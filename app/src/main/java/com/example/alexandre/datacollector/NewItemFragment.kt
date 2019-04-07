package com.example.alexandre.datacollector


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.AddNewItemBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class NewItemFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AddNewItemBinding>(inflater, R.layout.add_new_item, container, false)
        binding.t1ContinueButton.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_newItemFragment_to_detailsFragment2)
        }
        return binding.root
    }


}
