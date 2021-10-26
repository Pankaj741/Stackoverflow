package com.example.stackoverflow.data.model

import androidx.annotation.Keep

@Keep
data class QuestionsData(
    val avgViewCount: Float?,
    val avgAnswerCount: Float?,
    val questions: MutableList<Question>?
)