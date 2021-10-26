package com.example.stackoverflow.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackoverflow.data.model.QuestionListResponse

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionsData(questionListResponse: QuestionListResponse) : Long

    @Query("SELECT * FROM questions_table")
    fun getQuestionsData() : QuestionListResponse?

    @Query("SELECT count(*) FROM questions_table")
    fun getCount() : Int
}