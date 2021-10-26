package com.example.stackoverflow.data.api

import com.example.stackoverflow.data.model.QuestionListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("2.2/questions")
    suspend fun getQuestionList(
        @Query("key") key: String,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String,
    ): QuestionListResponse
}