package com.example.SMARTFRIDGE.Databasing

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.SMARTFRIDGE.Conversions
import androidx.room.TypeConverters
import androidx.room.Room


//////////////////////////////////////////////////////////
@Database(entities = [FoodMenu::class], version = 1) // 1 table for foodmenu objects
@TypeConverters
    (Conversions::class) // Room use conversions

abstract class FoodMenuDATABASE: RoomDatabase() { // room needs abstract class for database
    abstract fun foodMenuDao(): FoodMenuDAO

    companion object { // STATIC, 1 DB instance exists
        @Volatile // makes chxs visible to all threads
        private var INSTANCE: FoodMenuDATABASE? = null // can be nullable DB at first ? set to null

        fun getDatabase(context: Context): FoodMenuDATABASE { // get instance of DB
            return INSTANCE ?: synchronized(this) { // runs inst if not null or just runs // only 1 thread can use, no simultaneous
                val instance = Room.databaseBuilder(
                    context.applicationContext, // prevent memory leak
                    FoodMenuDATABASE::class.java,
                    "foodmenu_database"
                ).build() // finalizes
                INSTANCE = instance // stores instance in variable
                instance // returns said instance
            }
        }
    }


}