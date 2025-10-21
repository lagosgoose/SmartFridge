package com.example.SMARTFRIDGE.Databasing

import androidx.room.Database
import androidx.room.RoomDatabase

//////////////////////////////////////////////////////////
@Database(entities = [FoodMenu::class], version = 1)

abstract class FoodMenuDATABASE: RoomDatabase() {
    abstract val dao: FoodMenuDAO


}