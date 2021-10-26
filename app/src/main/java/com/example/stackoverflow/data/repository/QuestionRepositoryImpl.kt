package com.example.stackoverflow.data.repository

import android.content.Context
import com.example.stackoverflow.data.api.ApiHelper
import com.example.stackoverflow.data.api.ApiHelperImpl
import com.example.stackoverflow.data.local.DatabaseService
import com.example.stackoverflow.data.model.QuestionListResponse
import com.example.stackoverflow.di.qualifier.ApplicationContext
import com.example.stackoverflow.utils.UtilFunctions
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelperImpl,
    private val database: DatabaseService,
    @ApplicationContext private val context: Context,
) : QuestionRepository {

    override suspend fun getQuestions(): QuestionListResponse? {
        return if (UtilFunctions.isNetworkAvailable(context)){
             apiHelper.getQuestionList("desc","activity","stackoverflow")
        } else {
            database.questionsDao().getQuestionsData()
        }
    }

    override suspend fun insertQuestionData(questionListResponse: QuestionListResponse) {
        database.questionsDao().insertQuestionsData(questionListResponse)
    }

    override suspend fun getCount(): Int {
        return database.questionsDao().getCount()
    }

}