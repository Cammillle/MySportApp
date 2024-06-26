package com.example.mysportproject.ui.fragments.nutrition.models.localdb

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.Companion.REAL
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_ru")
data class FoodProductRu(
    @PrimaryKey
    @ColumnInfo(name = "id") override var id: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "weight", typeAffinity = REAL) val weight: Float,
    @ColumnInfo(name = "proteins", typeAffinity = REAL) override val proteins: Float,
    @ColumnInfo(name = "fats", typeAffinity = REAL) override val fats: Float,
    @ColumnInfo(name = "carbs", typeAffinity = REAL) override val carbs: Float,
    @ColumnInfo(name = "calories", typeAffinity = REAL) override val calories: Float
) : IProduct

interface IProduct {
    val name: String
    val proteins: Float
    val fats: Float
    val carbs: Float
    val calories: Float
    var id: Int
}