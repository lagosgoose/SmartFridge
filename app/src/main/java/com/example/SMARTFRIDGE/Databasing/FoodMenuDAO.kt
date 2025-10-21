package com.example.SMARTFRIDGE.Databasing

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodMenuDAO {

    @Insert
    suspend fun addFood(foodMenu: FoodMenu)

    @Update
    suspend fun updateFood(foodMenu: FoodMenu)

    @Delete
    suspend fun deleteFood(foodMenu: FoodMenu)

    @Query("SELECT * FROM food_menu ORDER BY itemNumberID ASC")
    suspend fun getAllFoods(): List<FoodMenu>

    @Query("SELECT * FROM food_menu ORDER BY foodName ASC")
    suspend fun getAllFoodsByName(): List<FoodMenu>

    @Query("SELECT * FROM food_menu ORDER BY expirationDate ASC")
    suspend fun getAllFoodsByExpirationDate(): List<FoodMenu>


}