package com.example.stackoverflow.ui.questionlist.viewholder

import android.view.View
import com.example.stackoverflow.databinding.ItemFilterBottomSheetBinding
import com.example.stackoverflow.ui.base.BaseViewHolder
import com.example.stackoverflow.ui.questionlist.SelectFilter

class ChooseFilterViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {

    private val binding = ItemFilterBottomSheetBinding.bind(itemView)
    var mSelectFilter: SelectFilter? = null

    override fun bind(data: String) {

        with(binding) {
            tvFilter.text = data
            root.setOnClickListener {
                mSelectFilter?.onSelectFilter(data)
            }
        }
    }

}