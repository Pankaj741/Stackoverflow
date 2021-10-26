package com.example.stackoverflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stackoverflow.data.local.dao.QuestionsDao
import com.example.stackoverflow.data.model.QuestionListResponse

@Database(
    entities = [
        QuestionListResponse::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DatabaseService: RoomDatabase() {

    abstract fun questionsDao(): QuestionsDao
}