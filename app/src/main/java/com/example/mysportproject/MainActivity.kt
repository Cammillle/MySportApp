package com.example.mysportproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.mysportproject.ui.fragments.fitness.viewmodels.PressViewModel
import com.example.mysportproject.ui.fragments.fitness.viewmodels.WholeBodyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val pressViewModel: PressViewModel by viewModels()
    private val wholeBodyModel: WholeBodyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pressViewModel.preferences = getSharedPreferences("press", MODE_PRIVATE)
        wholeBodyModel.preferences = getSharedPreferences("whole_body", MODE_PRIVATE)

    }
}