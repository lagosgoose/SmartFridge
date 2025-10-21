package com.example.SMARTFRIDGE.Databasing

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_menu")
data class FoodMenu(
    @PrimaryKey(autoGenerate = true)
    val itemNumberID: Int = 0,
    ///////////////////////////////
    val foodName: String,
    val expirationDate: String,

)