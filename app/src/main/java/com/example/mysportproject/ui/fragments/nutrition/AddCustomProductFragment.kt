package com.example.mysportproject.ui.fragments.nutrition

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mysportproject.R
import com.example.mysportproject.databinding.FragmentAddCustomProductBinding
import com.example.mysportproject.databinding.FragmentNutritionBinding
import com.example.mysportproject.ui.fragments.nutrition.util.toFloatOrZero
import com.example.mysportproject.ui.fragments.nutrition.util.toast

class AddCustomProductFragment : Fragment() {
    lateinit var binding: FragmentAddCustomProductBinding

    lateinit var modelFactory: ViewModelProvider.Factory
    private val addViewModel by lazy {
        ViewModelProvider(this, modelFactory).get(NutritionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFab()
        setEnterListeners()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCustomProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun setEnterListeners() = with(binding) {

        activityAddQuantityEdtText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                activityAddCaloriesEdtText.requestFocus()
                return@OnKeyListener true
            }
            false
        })

        activityAddCaloriesEdtText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                activityAddTitleProteinsEdtText.requestFocus()
                return@OnKeyListener true
            }
            false
        })

        // proteins
        activityAddTitleProteinsEdtText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                activityAddTitleFatsEdtText.requestFocus()
                return@OnKeyListener true
            }
            false
        })
        // carbs
        activityAddTitleFatsEdtText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                activityAddTitleCarbsEdtText.requestFocus()
                return@OnKeyListener true
            }
            false
        })
        // fats
        activityAddTitleCarbsEdtText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                activityAddTitleCarbsEdtText.clearFocus()
                return@OnKeyListener true
            }
            false
        })

    }


    private fun setFab() {
        try {
            // add white icon to the fab
            binding.activityAddFab.setOnClickListener(fabListener)
            val myFabSrc = ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.ic_check_24, null)      //R.drawable.ic_check_24
            val willBeWhite = myFabSrc?.constantState?.newDrawable()
            willBeWhite?.mutate()?.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            willBeWhite?.let { binding.activityAddFab.setImageDrawable(it) }
        } catch (e: Exception) {
            binding.activityAddFab.setOnClickListener(fabListener)
            e.printStackTrace()
        }
    }

    private val fabListener = View.OnClickListener   {

            val gramsInOnePortion = binding.activityAddQuantityEdtText.text.toString().toFloatOrZero()
            val caloriesInOnePortion =binding.activityAddCaloriesEdtText.text.toString().toFloatOrZero()
            val proteins =binding.activityAddTitleProteinsEdtText.text.toString().toFloatOrZero()
            val fats =binding.activityAddTitleFatsEdtText.text.toString().toFloatOrZero()
            val carbs =binding.activityAddTitleCarbsEdtText.text.toString().toFloatOrZero()
            val calories =binding.activityAddCaloriesEdtText.text.toString().toFloatOrZero()
            val grams =binding.activityAddQuantityEdtText.text.toString().toFloatOrZero()

            // handle calories errors
            if (binding.activityAddCaloriesEdtText.text.isEmpty() || calories == 0f) {
                binding.activityAddCaloriesEdt.isErrorEnabled = true
                binding.activityAddCaloriesEdt.error = getString(R.string.error_calories)
                return@OnClickListener
            } else {
                binding.activityAddCaloriesEdt.isErrorEnabled = false
            }
            // handle grams errors
            if (binding.activityAddQuantityEdtText.text.isEmpty() || grams == 0f) {
                binding.activityAddQuantityEdt.isErrorEnabled = true
                binding.activityAddQuantityEdt.error = getString(R.string.error_grams)
                return@OnClickListener
            } else {
                binding.activityAddQuantityEdt.isErrorEnabled = false
            }

            // handle name errors
            if (binding.activityAddNameEdt.text != null && (binding.activityAddNameEdt.text!!.isEmpty()
                        || binding.activityAddNameEdt.text!!.isBlank())
            ) {
                binding.activityAddNameEdt.error = getString(R.string.error_specify_product_name)
                return@OnClickListener
            }

            // proteins
            when {
                binding.activityAddTitleProteinsEdtText.text.isEmpty() -> {
                    binding.activityAddTitleProteinsEdt.isErrorEnabled = true
                    binding.activityAddTitleProteinsEdt.error = getString(R.string.error_specify_proteins)
                    return@OnClickListener

                }
                addViewModel.exceeds(gramsInOnePortion, proteins) -> {
                    binding.activityAddTitleProteinsEdt.isErrorEnabled = true
                    binding.activityAddTitleProteinsEdt.error =
                        getString(R.string.error_bigger_than, gramsInOnePortion.toInt())
                    return@OnClickListener
                }
                else -> {
                    binding.activityAddTitleProteinsEdt.isErrorEnabled = false
                }
            }

            // fats
            when {
                binding.activityAddTitleFatsEdtText.text.isEmpty() -> {
                    binding.activityAddTitleFatsEdt.isErrorEnabled = true
                    binding.activityAddTitleFatsEdt.error = getString(R.string.error_specify_fats)
                    return@OnClickListener
                }
                addViewModel.exceeds(gramsInOnePortion, fats) -> {
                    binding.activityAddTitleFatsEdt.isErrorEnabled = true
                    binding.activityAddTitleFatsEdtText.error =
                        getString(R.string.error_bigger_than, gramsInOnePortion.toInt())
                    return@OnClickListener
                }
                else -> {
                    binding.activityAddTitleFatsEdt.isErrorEnabled = false
                }
            }

            // carbs
            when {
                binding.activityAddTitleCarbsEdtText.text.isEmpty() -> {
                    binding.activityAddTitleCarbsEdt.isErrorEnabled = true
                    binding.activityAddTitleCarbsEdt.error = getString(R.string.error_specify_carbs)
                    return@OnClickListener
                }
                addViewModel.exceeds(gramsInOnePortion, carbs) -> {
                    binding.activityAddTitleCarbsEdt.isErrorEnabled = true
                    binding.activityAddTitleCarbsEdt.error =
                        getString(R.string.error_bigger_than, gramsInOnePortion.toInt())
                    return@OnClickListener
                }
                else -> {
                    binding.activityAddTitleCarbsEdt.isErrorEnabled = false
                }
            }


            val hundredMultiplier = addViewModel.getMultiplier(gramsInOnePortion)
            val caloriesInHundredGrams =
                addViewModel.getCalories(caloriesInOnePortion, hundredMultiplier)
            val energy = addViewModel.getEnergy(caloriesInHundredGrams)
            val name =binding.activityAddNameEdt.text.toString()


            val proteinsInHundred = if (!binding.activityAddTitleProteinsEdtText.text.isNullOrEmpty())
                binding.activityAddTitleProteinsEdtText.text.toString().toFloat() * hundredMultiplier
            else 0f
            val fatsInHundred = if (!binding.activityAddTitleFatsEdtText.text.isNullOrEmpty())
                binding.activityAddTitleFatsEdtText.text.toString().toFloat() * hundredMultiplier
            else 0f
            val carbsInHundred = if (!binding.activityAddTitleCarbsEdtText.text.isNullOrEmpty())
                binding.activityAddTitleCarbsEdtText.text.toString().toFloat() * hundredMultiplier
            else 0f

            if (addViewModel.exceeds(
                    gramsInOnePortion,
                    carbs + proteins
                )
            ) {
                requireContext().toast(getString(R.string.error_exceed_nutrients, gramsInOnePortion))
                return@OnClickListener
            }
            // save product based on user inputs
            addViewModel.saveProduct(
                name,
                energy,
                proteinsInHundred,
                fatsInHundred,
                carbsInHundred,
            )


    }

    companion object {

        @JvmStatic
        fun newInstance() = AddCustomProductFragment()
    }
}