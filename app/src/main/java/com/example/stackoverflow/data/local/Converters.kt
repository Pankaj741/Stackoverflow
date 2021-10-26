package com.example.stackoverflow.data.local

import androidx.room.TypeConverter
import com.example.stackoverflow.data.model.Question
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<Question>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Question>::class.java).toList()
}