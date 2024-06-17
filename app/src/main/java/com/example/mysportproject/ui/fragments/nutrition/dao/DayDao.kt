package com.example.mysportproject.ui.fragments.nutrition.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mysportproject.ui.fragments.nutrition.models.day.Day
import com.example.mysportproject.ui.fragments.nutrition.models.day.Meal

interface DayDao {

    @Query("select * from days")
    fun getAllDays(): List<Day>

    @Query("select * from days where month == :month limit 31")
    fun getAllDaysInMonth(month: String): List<Day>

    @Query("select * from days where month == :month and day == :day")
    fun searchByStringDate(month:String, day: String): Day?

    @Query("select * from days where dayId == :id")
    fun searchById(id: Int): Day?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(day: Day)

    @Query("select rowid from days order by ROWID desc limit 1")
    fun getSize(): Int

    @Query("select * from days order by ROWID desc limit 1")
    fun getLastDay(): Day

    @Query("delete from days where dayId == :id")
    fun deleteDay(id: Int)

    @Query("delete from days")
    fun deleteAllDays()

    @Query("select * from meals")
    fun getAllProducts(): List<Meal>

    @Query("update days set waterNum=:waterNum where dayId == :id")
    fun updateWaterGlasses(waterNum: Int, id: Int)

}