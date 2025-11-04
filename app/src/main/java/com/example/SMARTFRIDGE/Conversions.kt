package com.example.SMARTFRIDGE

import androidx.room.TypeConverter
import java.time.LocalDate

class Conversions {
    // long to Localdate
    @TypeConverter
    fun fromTimeStamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }
// year/ month / day
    // localdate to long so how many days since EPOCH
    @TypeConverter
    fun dateToTimeStamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
// store as long and retreive as localdate
    }


