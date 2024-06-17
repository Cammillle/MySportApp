package com.example.mysportproject.ui.fragments.nutrition.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportproject.ui.fragments.nutrition.models.day.Day
import com.example.mysportproject.ui.fragments.nutrition.models.user.User
import com.example.mysportproject.ui.fragments.nutrition.models.user.UserLeftValues
import com.example.mysportproject.ui.fragments.nutrition.repositories.DayRepository
import com.example.mysportproject.ui.fragments.nutrition.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject


/**
 *
 * ViewModel is shared by HomeActivity, HomeFragment, HomeDownFragment, HomeUpFragment and
 * DayFragment
 */


class HomeViewModel @Inject internal constructor(
    private val dayRepository: DayRepository,
    private val userRepository: UserRepository,
    private val spHelper: SharedPrefsHelper
) : ViewModel() {

    val isResettingDateOrSwiping = MutableLiveData(false)

    val currentDay: LiveData<Day> = dayRepository.currentDay

    val currentBottomPosition = MutableLiveData(FragmentNavigationType.HOME)

    val currentPosition: LiveData<Int> = dayRepository.currentPosition

    val user: LiveData<User?> = userRepository.user

    val userLeftValues: LiveData<UserLeftValues> = userRepository.userLeftValues

    fun introShowed() = spHelper.isIntroShowed()

    // update left values automatically when user is changed
    val userData = Transformations.map(user) {
        it?.let {
            userRepository.updateLeftValues(currentDay.value)
        }
    }

    val dayData = Transformations.map(currentDay) {
        it?.let {
            userRepository.updateLeftValues(it)
            dayRepository.checkFirebaseVersion(it)
        }
    }


    fun updateDayAndDate(position: Int? = null) {
        isResettingDateOrSwiping.value?.let {
            if (it) return
        } ?: return
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.updateDay(position)
        }
    }


    fun resetDate() {
        dayRepository.resetDate()
    }

    fun saveHomeUiGuideShowed() =
        spHelper.saveHomeUiGuideShowed()


    fun isHomeGuideShowed(): Boolean = spHelper.isHomeGuideShowed()

    // get date to display in UI
    fun getDateText(): CharSequence =
        "${currentDay.value?.date?.day}.${currentDay.value?.date?.month}"

    fun setWater(i: Int) {
        dayRepository.updateWaterNum(i)
    }

    fun downloadLocalDB(
        filePath: String,
        code: String,
        result: BasicOperationCallback
    ) = viewModelScope.launch(Dispatchers.IO) {
        fun download(link: String, path: String) {
            try {
                URL(link).openStream().use { input ->
                    FileOutputStream(File(path)).use { output ->
                        input.copyTo(output)
                        input.close()
                        result.onSuccess()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.onFailure()
            }
        }
        LocalDBUrls.getURLByCode(code)?.let { download(it, filePath) }
    }


}
