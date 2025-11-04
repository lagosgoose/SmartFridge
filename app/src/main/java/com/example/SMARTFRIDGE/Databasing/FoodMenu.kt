package com.example.SMARTFRIDGE.Databasing

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "food_menu")
data class FoodMenu @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val itemNumberID: Int = 0,
    ///////////////////////////////
    val foodName: String,
    val expirationDate: LocalDate,
    val dateAdded: LocalDate = LocalDate.now()
)