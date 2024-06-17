package com.example.mysportproject.ui.fragments.nutrition.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentProducts")
data class RecentProduct(
    @PrimaryKey
    val name: String,
    val foodProductId: Int
)