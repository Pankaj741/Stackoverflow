package com.example.stackoverflow.data.api

import com.example.stackoverflow.data.model.QuestionListResponse

interface ApiHelper {

    suspend fun getQuestionList(order: String,
                                sort: String,
                                site: String): QuestionListResponse
}