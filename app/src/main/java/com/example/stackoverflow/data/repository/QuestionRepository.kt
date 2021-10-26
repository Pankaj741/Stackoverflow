package com.example.stackoverflow.data.repository

import com.example.stackoverflow.data.model.QuestionListResponse

interface QuestionRepository {

    suspend fun getQuestions(): QuestionListResponse?

    suspend fun insertQuestionData(questionListResponse: QuestionListResponse)

    suspend fun getCount(): Int
}