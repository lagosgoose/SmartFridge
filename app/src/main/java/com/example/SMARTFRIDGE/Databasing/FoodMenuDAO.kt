package com.example.SMARTFRIDGE.Databasing

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodMenuDAO {

    @Insert
    suspend fun addFood(foodMenu: FoodMenu)

    @Update
    suspend fun updateFood(foodMenu: FoodMenu)

    @Delete
    suspend fun deleteFood(foodMenu: FoodMenu)
//////////////////////// SORTING ////////////////////////////////////////
    @Query("SELECT * FROM food_menu ORDER BY itemNumberID ASC")
    fun getAllFoods(): Flow<List<FoodMenu>>

    @Query("SELECT * FROM food_menu ORDER BY foodName ASC")
    fun getAllFoodsByName(): Flow<List<FoodMenu>>

    @Query("SELECT * FROM food_menu ORDER BY expirationDate ASC")
    fun getAllFoodsByExpirationDate(): Flow<List<FoodMenu>>
//////////// getting a COUNT ////////////////
    @Query("SELECT COUNT(*) FROM food_menu")
    fun getFoodCount(): Flow<Int>

}
// suspend for 1 time ops,, or single result,, Flow is asyncronous