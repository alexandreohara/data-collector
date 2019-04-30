package com.example.alexandre.datacollector


import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.alexandre.datacollector.databinding.PasswordBinding
import kotlinx.android.synthetic.main.password.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var binding = DataBindingUtil.inflate<PasswordBinding>(inflater, R.layout.password, container, false)

        binding.access.setOnClickListener {
            if (binding.password.text.toString() == "2904") {
                binding.password.text = null
                it.findNavController().navigate(R.id.action_passwordFragment_to_welcomeFragment)
            } else {
                binding.password.text = null
                binding.password.error = "Senha Incorreta!"
                //Toast.makeText(context, "Senha incorreta!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
