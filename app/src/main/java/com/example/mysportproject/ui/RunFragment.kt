package com.example.mysportproject.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.mysportproject.R
import com.example.mysportproject.databinding.FragmentRunBinding
import com.example.mysportproject.databinding.FragmentStartBinding
import com.example.mysportproject.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.mysportproject.other.TrackingUtility
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RunFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentRunBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        requestPermissions()

        binding.bBackToStart.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_startFragment)
        }
        binding.imMap.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
    override fun onPermissionsGranted(p0: Int, p1: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }



    private fun initSpinner(){
        val spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner,
            R.layout.spinner_item
        ).also {adapter->
            adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RunFragment()

    }


}