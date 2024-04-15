package com.example.mysportproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mysportproject.R
import com.example.mysportproject.databinding.FragmentTrackingBinding
import com.example.mysportproject.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.mysportproject.services.TrackingService
import com.google.android.gms.maps.GoogleMap

class TrackingFragment : Fragment() {
    lateinit var binding:FragmentTrackingBinding
    private var map:GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            map = it
        }
        binding.bStart.setOnClickListener{
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }



        binding.bBack.setOnClickListener {
            findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
        }
    }

    private fun sendCommandToService(action:String) =
        Intent(requireContext(),TrackingService::class.java).also { intent ->  
            intent.action = action
            requireContext().startService(intent)
        }





    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TrackingFragment()
    }
}