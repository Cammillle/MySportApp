package com.example.mysportproject.ui.fragments.nutrition.dao

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.mysportproject.ui.fragments.nutrition.models.FoodProduct
import com.example.mysportproject.ui.fragments.nutrition.models.localdb.IProduct
import com.example.mysportproject.ui.fragments.nutrition.util.FOOD_DATABASE_ENTRIES


@Dao
interface FoodProductDao {

    @Query("select * from food")
    fun getAllFood():List<FoodProduct>

    @Query("select * from food where name like '%' || :name || '%'")
    fun searchByName(name: String):List<FoodProduct>

    @Query("select * from food where id == :id")
    fun searchById(id: Int):FoodProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: FoodProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<FoodProduct>)

    @Query("select rowid from food order by ROWID desc limit 1")
    fun getSize():Int

    @Delete
    fun deleteProduct(product: FoodProduct)

    @Query("select * from food where id > $FOOD_DATABASE_ENTRIES")
    fun getCustomProducts(): List<FoodProduct>

    @Query("delete from food where id > $FOOD_DATABASE_ENTRIES")
    fun deleteCustomProducts()

    @Query("select * from food where name == :name")
    fun searchByNameExact(name: String): FoodProduct

    @Query("select name from food where name like '%' || :suggestion || '%'")
    fun searchBySuggestion(suggestion: String): List<String>?

}