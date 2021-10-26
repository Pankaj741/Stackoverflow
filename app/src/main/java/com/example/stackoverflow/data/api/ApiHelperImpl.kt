package com.example.stackoverflow.data.api

import com.example.stackoverflow.data.model.QuestionListResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
    private val key: String,
) : ApiHelper {

    override suspend fun getQuestionList(order: String, sort: String, site: String): QuestionListResponse =
        apiService.getQuestionList(key, order, sort, site)
}