package com.example.mysportproject.ui.fragments.nutrition.models.day

import com.google.gson.Gson
import java.io.Serializable

data class DayDate(
    val month: String = "01",
    val day: String = "01",
    val year: String = "2020"
) : Serializable {

    override fun toString(): String {
        return "$day.$month.$year"
    }
    fun toJson(): String? {
        return Gson().toJson(this)
    }

}
