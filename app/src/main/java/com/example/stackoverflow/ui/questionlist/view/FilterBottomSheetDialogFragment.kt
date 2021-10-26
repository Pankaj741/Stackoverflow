package com.example.stackoverflow.ui.questionlist.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflow.R
import com.example.stackoverflow.databinding.FragmentFilterBottomSheetDialogBinding
import com.example.stackoverflow.ui.questionlist.SelectFilter
import com.example.stackoverflow.ui.questionlist.adapter.ChooseFilterAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterBottomSheetDialogFragment(private val mSelectFilter: SelectFilter) :
    BottomSheetDialogFragment() {

    companion object {
        const val TAG = "FilterBottomSheetDialogFragment"
        private const val PARAMS_FILTERS = "filters"

        fun newInstance(
            filters: ArrayList<String>,
            mSelectFilter: SelectFilter
        ) = FilterBottomSheetDialogFragment(mSelectFilter).apply {
            arguments = Bundle().apply {
                putStringArrayList(PARAMS_FILTERS, filters)
            }
        }
    }

    private var binding: FragmentFilterBottomSheetDialogBinding? = null
    private val filterAdapter by lazy { ChooseFilterAdapter(mSelectFilter) }
    private val args by lazy { arguments }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding?.apply {
            rvFilter.adapter = filterAdapter
            rvFilter.layoutManager = LinearLayoutManager(context)
            filterAdapter.updateList(args?.getStringArrayList(PARAMS_FILTERS).orEmpty())
            tvClear.setOnClickListener {
                mSelectFilter.onClearFilter()
                dismiss()
            }
            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}