package com.example.stackoverflow.ui.questionlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflow.R
import com.example.stackoverflow.data.model.*
import com.example.stackoverflow.data.repository.QuestionRepository
import com.example.stackoverflow.data.repository.QuestionRepositoryImpl
import com.example.stackoverflow.utils.Outcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class QuestionListViewModel @Inject constructor(
    private val questionRepository: QuestionRepositoryImpl
) : ViewModel() {

    private val _questionListLiveData: MutableLiveData<Outcome<Pair<QuestionsData, Boolean>>> =
        MutableLiveData()
    val questionListLiveData: LiveData<Outcome<Pair<QuestionsData, Boolean>>>
        get() = _questionListLiveData

    private val _noResultFoundLiveData: MutableLiveData<NoResultData> = MutableLiveData()
    val noResultFoundLiveData: LiveData<NoResultData>
        get() = _noResultFoundLiveData

    // Advertising Banner list data
    private val _advertiseBannerList: List<Banner> = listOf(
        Banner("https://picsum.photos/300/150"),
        Banner("https://picsum.photos/300/150"),
        Banner("https://picsum.photos/300/150"),
        Banner("https://picsum.photos/300/150"),
        Banner("https://picsum.photos/300/150")
    )

    private lateinit var _initialQuestionsData: QuestionsData

    // Store filter tags
    private var _filterTagSet = mutableSetOf<String>()
    private var _filterList = arrayListOf<String>()

    fun fetchQuestionList() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoader(true)
            try {
                val response = questionRepository.getQuestions()
                var avgViewCount = 0f
                var avgAnswerCount = 0f

                // Calculate average view count, average answer count and collect all the filter tags
                if (response?.items != null) {
                    response.items?.map { question ->
                        question.tags.forEach {
                            _filterTagSet.add(it)
                        }
                        avgViewCount += question.viewCount
                        avgAnswerCount += question.answerCount
                    }
                    _filterList = _filterTagSet.toList() as ArrayList<String>
                    if (response.items?.size != 0) {
                        avgViewCount /= response.items?.size ?: 1
                    }
                } else {

                    // set data for no result view
                    _noResultFoundLiveData.postValue(
                        NoResultData(
                            title = "Oops, No data to reflect",
                            subtitle = "You need to connect at least once with active internet"
                        )
                    )
                }
                val questionsData = QuestionsData(
                    avgViewCount = avgViewCount,
                    avgAnswerCount = avgAnswerCount,
                    questions = response?.items?.toMutableList()
                )

                // save initial network response to perform searching and filtering
                _initialQuestionsData = questionsData
                _questionListLiveData.postValue(Outcome.success(Pair(questionsData, true)))
                setLoader(false)

                //Caching data
                if (questionRepository.getCount() == 0 && response != null) {
                    questionRepository.insertQuestionData(response)
                }
            } catch (e: Exception) {
                setLoader(false)
                _questionListLiveData.postValue(Outcome.apiError(e))
                e.printStackTrace()
            }
        }
    }

    /**
     * Apply filter or search for a given query.
     * @param isSearching tells what does function will do (searching or filtering).
     */
    fun applyFilterOrSearch(query: String, isSearching: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            setLoader(true)

            // isBannerShown set the visibility of advertising banner in the list
            var isBannerShown = false
            val newQuestionData: QuestionsData = if (query.isBlank()) {
                isBannerShown = true
                _initialQuestionsData
            } else {
                val questionList = mutableListOf<Question>()
                var avgViewCount = 0f
                var avgAnswerCount = 0f
                _initialQuestionsData.questions.orEmpty().filter {
                    if (isSearching) {
                        it.title.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
                                || it.owner.displayName.lowercase(Locale.ROOT)
                            .contains(query.lowercase(Locale.ROOT))
                    } else {
                        it.tags.contains(query)
                    }
                }.forEach { question ->
                    avgAnswerCount += question.answerCount
                    avgViewCount += question.viewCount
                    questionList.add(question)
                }

                if (questionList.size != 0) {
                    avgAnswerCount /= questionList.size
                    avgViewCount /= questionList.size
                } else {
                    setLoader(false)
                    _noResultFoundLiveData.postValue(
                        NoResultData(
                            title = "No result found",
                            subtitle = "Try something else"
                        )
                    )
                    return@launch
                }
                QuestionsData(
                    avgAnswerCount = avgAnswerCount,
                    avgViewCount = avgViewCount,
                    questions = questionList
                )
            }
            setLoader(false)
            _questionListLiveData.postValue(Outcome.success(Pair(newQuestionData, isBannerShown)))
        }
    }

    fun removeFilter() {
        _questionListLiveData.postValue(Outcome.success(Pair(_initialQuestionsData, true)))
    }

    fun getBannerList() = _advertiseBannerList

    fun getFilterList() = _filterList

    // merge list of questions and advertising banners
    fun mergerList(
        questionList: MutableList<Question>?,
        bannerList: List<Banner>
    ): List<WidgetData> {
        if (questionList == null) return emptyList()
        val newList: MutableList<WidgetData> = questionList.toMutableList()
        val random = Random()
        bannerList.forEach {
            newList.add(random.nextInt(questionList.size), it)
        }
        return newList
    }

    private suspend fun setLoader(isLoading: Boolean) {
        withContext(Dispatchers.Main) {
            _questionListLiveData.postValue(Outcome.loading(isLoading))
        }
    }
}