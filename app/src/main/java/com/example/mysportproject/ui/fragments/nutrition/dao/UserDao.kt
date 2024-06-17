package com.example.mysportproject.ui.fragments.nutrition.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mysportproject.ui.fragments.nutrition.models.MeasureType
import com.example.mysportproject.ui.fragments.nutrition.models.user.User

@Dao
interface UserDao {

    @Query("select caloriesNeeded from user where uid == 0")
    fun getCalories(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun update(user: User)

    @Query("delete from user")
    fun deleteAllUsers()

    @Query("select * from user where uid == 0")
    fun getUser(): User?


    @Query("select measureType from user where uid == 0")
    fun getMeasure(): MeasureType?



}