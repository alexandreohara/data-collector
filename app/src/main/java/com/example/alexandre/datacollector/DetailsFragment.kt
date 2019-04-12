package com.example.alexandre.datacollector


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.DetailsBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : Fragment() {

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<DetailsBinding>(inflater, R.layout.details, container, false)
        binding.t2ContinueButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_detailsFragment2_to_finalDetailFragment)
        }
        return binding.root
    }

}
