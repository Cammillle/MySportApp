package com.example.mysportproject.ui.fragments.nutrition.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mysportproject.ui.fragments.nutrition.dao.FoodProductDao
import com.example.mysportproject.ui.fragments.nutrition.models.FoodProduct
import com.example.mysportproject.ui.fragments.nutrition.models.day.Meal


/**
 * Database class to obtain information about food products from local DB
 */
@Database(entities = [FoodProduct::class, Meal::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun getProductDao(): FoodProductDao

    companion object {

        private const val FOOD_DATABASE_NAME = "foodProducts"
        private const val FOOD_DATABASE_PATH = "databases/food.db"

        @Volatile
        private var instance: FoodDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance

            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java,
                FOOD_DATABASE_NAME
            )
            .createFromAsset(FOOD_DATABASE_PATH)
            .build()

    }


}