package com.example.mysportproject.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mysportproject.R
import com.example.mysportproject.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding:FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bRun.setOnClickListener {

            //binding.bRun.setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.checked_blue))
            findNavController().navigate(R.id.action_startFragment_to_runFragment)
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}