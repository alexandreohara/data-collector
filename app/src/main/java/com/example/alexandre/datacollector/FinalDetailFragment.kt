package com.example.alexandre.datacollector

import android.app.AlertDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
/**
 * A simple [Fragment] subclass.
 *
 */
class FinalDetailFragment : Fragment() {

    private lateinit var binding: FinalDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.final_detail, container, false)

        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Seu item foi registrado com sucesso!")
        dialog.setPositiveButton("Finalizar") { dialog, which->
            navigateHome()
        }

        binding.t3FinishButton.setOnClickListener { view ->
            //view.findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
            dialog.show()
        }
        binding.t3CameraButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
        }
        return binding.root
    }

    private fun navigateHome() {
        binding.t3FinishButton.findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
    }


}
