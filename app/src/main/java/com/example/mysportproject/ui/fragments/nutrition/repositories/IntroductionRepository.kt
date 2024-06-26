package com.example.mysportproject.ui.fragments.nutrition.repositories

import androidx.lifecycle.MutableLiveData
import com.example.mysportproject.ui.fragments.nutrition.models.user.USER_BASIC_AGE
import com.example.mysportproject.ui.fragments.nutrition.models.user.USER_BASIC_HEIGHT
import com.example.mysportproject.ui.fragments.nutrition.models.user.USER_BASIC_SEX
import com.example.mysportproject.ui.fragments.nutrition.models.user.USER_BASIC_WEIGHT
import com.example.mysportproject.ui.fragments.nutrition.models.user.UserActivityLevel
import com.example.mysportproject.ui.fragments.nutrition.models.user.UserGoal
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class IntroductionRepository @Inject constructor() {


    // false - woman, true - man
    private val _sex = MutableLiveData(USER_BASIC_SEX)

    val goal = MutableLiveData(UserGoal.EAT_HEALTHIER)
    private val _weight: MutableLiveData<Int> = MutableLiveData(USER_BASIC_WEIGHT)
    private val _height: MutableLiveData<Int> = MutableLiveData(USER_BASIC_HEIGHT)
    private val _age = MutableLiveData(USER_BASIC_AGE)

    private val activityLevel = MutableLiveData(UserActivityLevel.LIGHT)
    val applied = MutableLiveData<Boolean>()
    private val sexChosen = MutableLiveData(false)
    private val activityChosen = MutableLiveData(false)

    private fun calculateCalories(): Int {
        return try {
            CaloriesCalculator().calculateCalories(
                true, _weight.value, _height.value,
                _age.value, activityLevel.value,
                _sex.value, goal.value
            )

        } catch (e: NullPointerException) {
            e.printStackTrace()
            CaloriesCalculator.CALORIES_MINIMUM
        }
    }

    fun canUnblockButton(length: Int, length1: Int, notEmpty: Boolean): Boolean {
        return length > 1 && length1 > 1 && notEmpty &&
                (activityChosen.value == true) && (sexChosen.value == true)
    }

    fun setActivityLevel(position: Int) {
        when (position) {
            0 -> {
                activityLevel.value = UserActivityLevel.LITTLE
            }
            1 -> {
                activityLevel.value = UserActivityLevel.LIGHT
            }
            2 -> {
                activityLevel.value = UserActivityLevel.MODERATE
            }
            3 -> {
                activityLevel.value = UserActivityLevel.HARD
            }
            4 -> {
                activityLevel.value = UserActivityLevel.VERY_HARD
            }
        }
    }

    suspend fun createUser(): User? = coroutineScope {
        async {
            if (valuesValid()) {
                val user = User(
                    caloriesNeeded = calculateCalories(), proteinsNeeded = calculateProteins(),
                    carbsNeeded = calculateCarbs(), fatsNeeded = calculateFats()
                )
                user
            } else null
        }.await()
    }

    private val HEIGHT_MAXIMUM = 230
    private val WEIGHT_MAXIMUM = 350

    private fun calculateCarbs(): Int {
        var carbsPercentage = 0.55
        if (goal.value != null) {
            // if goal is to lose weight, decrease carbs percentage
            if (goal.value == UserGoal.LOSE) carbsPercentage = 0.45
            // if goal is to gain weight, increase carbs percentage
            else if (goal.value == UserGoal.GAIN) carbsPercentage = 0.65
        }
        val carbs = (calculateCalories() * carbsPercentage) / 4
        return carbs.toInt()
    }

    private fun calculateFats(): Int {
        // 20 -35 % calories. 9 cal per gram
        var fatsPercentage = 0.27
        if (goal.value != null) {
            if (goal.value == UserGoal.LOSE) fatsPercentage = 0.22
            else if (goal.value == UserGoal.GAIN) fatsPercentage = 0.33
        }
        val carbs = (calculateCalories() * fatsPercentage) / 9
        return carbs.toInt()
    }

    fun areHeightWeightInvalid(text: String, text1: String): Boolean {
        return text.toInt() > HEIGHT_MAXIMUM || text1.toInt() > WEIGHT_MAXIMUM
    }

    private fun valuesValid(): Boolean {
        if (_weight.value == null
            || activityLevel.value == null
            || _height.value == null
            || _sex.value == null
            || _age.value == null
            || goal.value == null
        ) return false
        return true

    }

    private fun calculateProteins(): Int {
        val proteinsNormMultiplicand = 0.8
        val proteins = _weight.value?.times(proteinsNormMultiplicand)?.toInt()
        return proteins ?: 0
    }

    fun chooseGoal(goal: UserGoal) = this.goal.postValue(goal)

    fun setWeightValue(toString: String) = try {
        _weight.value = toString.toInt()
    } catch (e: Exception) {
    }

    fun setAgeValue(toString: String) = try {
        _age.value = toString.toInt()
    } catch (e: Exception) {
    }

    fun setHeightValue(toString: String) = try {
        _height.value = toString.toInt()
    } catch (e: Exception) {
    }

    fun setAppliedValue(b: Boolean) {
        applied.value = b
    }

    fun setActivityChosen(b: Boolean) {
        activityChosen.value = b
    }

    fun sexChosenValue(b: Boolean) {
        sexChosen.value = b
    }

    fun sexValue(b: Boolean) {
        _sex.value = b
    }


}