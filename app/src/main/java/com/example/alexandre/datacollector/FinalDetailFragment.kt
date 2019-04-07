package com.example.alexandre.datacollector


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FinalDetailBinding>(inflater, R.layout.final_detail, container, false)
        return binding.root
    }


}
