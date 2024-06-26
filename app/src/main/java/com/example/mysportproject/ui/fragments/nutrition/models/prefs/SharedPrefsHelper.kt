package com.example.mysportproject.ui.fragments.nutrition.models.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefsHelper @Inject internal constructor(private val sp: SharedPreferences) {

    companion object {
        private const val SP_CAUTION_DIALOG = "ShouldShowCautionDialog"
        private const val SP_KEY_INTRO_PRODUCTS = "productsUi"
    }

    fun saveDontAskDownloadDB() =
        sp.edit().putBoolean(SearchActivity.SP_KEY_LOCALE_ASK, false).commit()

    fun saveLocalDBDownloaded(code: String) =
        sp.edit().putString(SearchActivity.SP_KEY_LOCALE_SEARCH, code).commit()

    fun shouldAskDownloadDB() =
        sp.getBoolean(SearchActivity.SP_KEY_LOCALE_ASK, true)

    fun isIntroShowed() = sp.getBoolean(SP_INTRO_SHOWED, false)

    fun isHomeGuideShowed(): Boolean =
        sp.getBoolean(SP_GUIDE_HOME, false)

    fun saveIntroductionPassed() {
        sp.edit().putBoolean(SP_INTRO_SHOWED, true).apply()
    }

    fun saveHomeUiGuideShowed() =
        sp.edit().putBoolean(SP_GUIDE_HOME, true).commit()

    fun getLocalSearchPreferred() =
        sp.getString(SearchActivity.SP_KEY_LOCALE_SEARCH, null)

    fun setLocalSearchPreferred(code: String?) =
        sp.edit().putString(SearchActivity.SP_KEY_LOCALE_SEARCH, code).commit()


    fun shouldShowCautionDialog(): Boolean = sp.getBoolean(SP_CAUTION_DIALOG, true)

    fun saveShowedCautionDialog() = sp.edit().putBoolean(SP_CAUTION_DIALOG, false).commit()

    fun isProductsUiGuideShowed() = sp.getBoolean(SP_KEY_INTRO_PRODUCTS, false)

    fun saveProductsUiGuideShowed() =
        sp.edit().putBoolean(SP_KEY_INTRO_PRODUCTS, true).apply()

}