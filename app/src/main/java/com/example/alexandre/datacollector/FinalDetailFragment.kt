package com.example.alexandre.datacollector

import android.app.Activity
import android.app.AlertDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.alexandre.datacollector.databinding.FinalDetailBinding
import java.util.*

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
        dialog.setNegativeButton("Adicionar novo item") { dialog, which ->
            navigateNewItem()
        }

        binding.t3FinishButton.setOnClickListener { view ->
            // view.findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
            binding.t3FinishButton.text = "Aguarde!"
            dialog.show()
        }

        return binding.root
    }

    private fun navigateHome() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_welcomeFragment)
    }

    private fun navigateNewItem() {
        findNavController().navigate(R.id.action_finalDetailFragment_to_newItemFragment)
    }
}
