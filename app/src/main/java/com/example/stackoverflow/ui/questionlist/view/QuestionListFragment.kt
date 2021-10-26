package com.example.stackoverflow.ui.questionlist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflow.MyApplication
import com.example.stackoverflow.R
import com.example.stackoverflow.data.model.WidgetData
import com.example.stackoverflow.databinding.FragmentQuestionListBinding
import com.example.stackoverflow.di.components.DaggerFragmentComponent
import com.example.stackoverflow.di.modules.FragmentModule
import com.example.stackoverflow.ui.base.SearchView
import com.example.stackoverflow.ui.base.ViewModelFactory
import com.example.stackoverflow.ui.questionlist.BannerWidgetListener
import com.example.stackoverflow.ui.questionlist.SelectFilter
import com.example.stackoverflow.ui.questionlist.adapter.QuestionAdapter
import com.example.stackoverflow.ui.questionlist.viewmodel.QuestionListViewModel
import com.example.stackoverflow.utils.Outcome
import com.example.stackoverflow.utils.hide
import com.example.stackoverflow.utils.show
import javax.inject.Inject

class QuestionListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val questionListViewModel: QuestionListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(QuestionListViewModel::class.java)
    }

    private lateinit var questionAdapter: QuestionAdapter
    private var binding: FragmentQuestionListBinding? = null
    private var widgetList: MutableList<WidgetData>? = null
    private var filterBottomSheetDialogFragment: FilterBottomSheetDialogFragment? = null

    override fun onAttach(context: Context) {
        getDependencies()
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        questionListViewModel.fetchQuestionList()
    }


    private fun setupUI() {
        questionAdapter = QuestionAdapter(
            object : BannerWidgetListener {
                override fun onClickCancel(position: Int) {
                    if (position != -1) {
                        widgetList?.removeAt(position)
                        questionAdapter.updateList(widgetList.orEmpty())
                    }
                }
            }
        )
        binding?.rvFilter?.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding?.apply {
            ivFilter.setOnClickListener {
                if (questionAdapter.itemCount == 0) {
                    showToast(getString(R.string.no_items))
                } else {
                    val mSelectFilter = object : SelectFilter {
                        override fun onSelectFilter(filter: String) {
                            questionListViewModel.applyFilterOrSearch(
                                query = filter,
                                isSearching = false
                            )
                            showToast("Filter Applied")
                            filterBottomSheetDialogFragment?.dismiss()
                        }

                        override fun onClearFilter() {
                            questionListViewModel.removeFilter()
                            showToast("Filter Removed")
                        }
                    }
                    filterBottomSheetDialogFragment = FilterBottomSheetDialogFragment.newInstance(
                        filters = questionListViewModel.getFilterList(),
                        mSelectFilter = mSelectFilter
                    )
                    filterBottomSheetDialogFragment?.show(
                        childFragmentManager,
                        FilterBottomSheetDialogFragment.TAG
                    )
                }
            }

            etSearch.setQueryListener(object : SearchView.QueryListener {
                override fun onQuery(query: String) {
                    questionListViewModel.applyFilterOrSearch(query, true)
                }
            })
        }
    }

    private fun setupObservers() {
        questionListViewModel.questionListLiveData.observe(viewLifecycleOwner) {

            when (it) {
                is Outcome.ApiError -> binding?.progressBar?.hide()
                is Outcome.BadRequest -> binding?.progressBar?.hide()
                is Outcome.Failure -> binding?.progressBar?.hide()
                is Outcome.Progress -> {
                    binding?.progressBar?.isVisible = it.loading
                }
                is Outcome.Success -> {
                    val data = it.data.first
                    binding?.apply {
                        questionsDataContainerGroup.show()
                        layoutNoResultFound.root.hide()
                        if (it.data.second) {
                            widgetList = questionListViewModel.mergerList(
                                data.questions, questionListViewModel.getBannerList()
                            ).toMutableList()
                            questionAdapter.updateList(
                                widgetList.orEmpty()
                            )
                        } else {
                            questionAdapter.updateList(data.questions.orEmpty())
                        }

                        layoutAverageViewCount.apply {
                            tvTitle.text = getString(R.string.average_view_count)
                            tvCount.text = String.format("%.2f", data.avgViewCount)
                        }

                        layoutAverageAnswerCount.apply {
                            tvTitle.text = getString(R.string.average_answer_count)
                            tvCount.text = String.format("%.2f", data.avgAnswerCount)
                        }
                    }
                }
            }
        }

        questionListViewModel.noResultFoundLiveData.observe(viewLifecycleOwner) { noResultContainer ->
            binding?.apply {
                questionsDataContainerGroup.hide()
                layoutNoResultFound.apply {
                    root.show()
                    noResultTvTitle.text = noResultContainer.title
                    noResultTvSubTitle.text = noResultContainer.subtitle
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((requireContext().applicationContext as MyApplication).applicationComponent)
            .fragmentModule(FragmentModule(this)) // this is shown as deprecated as no instance provided by it is being injected
            .build()
            .inject(this)
    }
}