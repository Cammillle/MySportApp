package com.example.mysportproject.ui.fragments.nutrition.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mysportproject.R
import com.example.mysportproject.ui.fragments.nutrition.viewmodels.IntroductionViewModel

@Suppress("DEPRECATION")
class FirstIntroductionFragment : Fragment()  {

//    override fun isPolicyRespected(): Boolean {
//        return false
//    }
//
//    override fun onUserIllegallyRequestedNextPage() {
//        return
//    }

    companion object {
        fun newInstance() =
            FirstIntroductionFragment()
    }

    private lateinit var introViewModel: IntroductionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container!!.setBackgroundColor(resources.getColor(R.color.white))
        activity?.let {
            introViewModel = ViewModelProvider(it).get(IntroductionViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragment_intro_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        intro_eat_healthier.setOnClickListener {
            introViewModel.chooseGoal(UserGoal.EAT_HEALTHIER)
        }
        intro_lose_weight.setOnClickListener {
            introViewModel.chooseGoal(UserGoal.LOSE)

        }
        intro_gain_weight.setOnClickListener {
            introViewModel.chooseGoal(UserGoal.GAIN)

        }
        activity?.let { act ->
            introViewModel.progressBar.observe(act, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    if (event.peekContent())
                        activity_intro_progressBar.visibility = View.VISIBLE
                    else activity_intro_progressBar.visibility = View.GONE
                }
            })
        }


    }


}
