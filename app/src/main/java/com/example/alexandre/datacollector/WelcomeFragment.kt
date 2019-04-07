package com.example.alexandre.datacollector


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import com.example.alexandre.datacollector.databinding.ActivityMainBinding
import com.example.alexandre.datacollector.databinding.WelcomeBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class WelcomeFragment : Fragment() {

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<WelcomeBinding>(inflater, R.layout.welcome, container, false)
        return binding.root
    }


}
