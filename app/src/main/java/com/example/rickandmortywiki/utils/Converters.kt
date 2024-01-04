package com.example.rickandmortywiki.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromListToString(listOfStrings: List<String>): String {
        return listOfStrings.joinToString(",")
    }
}
