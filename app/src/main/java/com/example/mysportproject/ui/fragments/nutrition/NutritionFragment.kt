package com.example.mysportproject.ui.fragments.nutrition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mysportproject.R
import com.example.mysportproject.databinding.FragmentNutritionBinding

class NutritionFragment : Fragment() {
    lateinit var binding:FragmentNutritionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            bAddBreakfast.setOnClickListener{
                findNavController().navigate(R.id.action_nutritionFragment_to_addCustomProductFragment)

            }


        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = NutritionFragment()
    }
}