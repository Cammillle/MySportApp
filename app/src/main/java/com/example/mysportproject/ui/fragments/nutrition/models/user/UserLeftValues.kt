package com.example.mysportproject.ui.fragments.nutrition.models.user

class UserLeftValues(
    var calories: Triple<Int, Boolean, Int> = Triple(0, false, 0),
    var proteinsLeft: Triple<Int, Boolean, Int> = Triple(0, false, 0),
    var carbsLeft: Triple<Int, Boolean, Int> = Triple(0, false, 0),
    var fatsLeft: Triple<Int, Boolean, Int> = Triple(0, false, 0)
)