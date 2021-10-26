package com.example.stackoverflow.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackoverflow.ui.questionlist.viewmodel.QuestionListViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val questionListViewModel: QuestionListViewModel
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionListViewModel::class.java)) {
            return questionListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}