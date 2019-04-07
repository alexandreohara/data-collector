package com.example.alexandre.datacollector


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.ActivityMainBinding
import com.example.alexandre.datacollector.databinding.WelcomeBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class WelcomeFragment : Fragment() {

    private lateinit var binding: WelcomeBinding

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome, container, false)
        binding.addMainButton.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_welcomeFragment_to_detailsFragment)
        }
        return binding.root
    }


    //The complete onClickListener with Navigation
//    binding.playButton.setOnClickListener { v: View ->
//        v.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//    }


}
