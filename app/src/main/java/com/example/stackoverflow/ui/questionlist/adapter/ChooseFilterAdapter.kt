package com.example.stackoverflow.ui.questionlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflow.R
import com.example.stackoverflow.ui.questionlist.SelectFilter
import com.example.stackoverflow.ui.questionlist.viewholder.ChooseFilterViewHolder

class ChooseFilterAdapter(private val mSelectFilter: SelectFilter?) :
    RecyclerView.Adapter<ChooseFilterViewHolder>() {

    private val filters = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseFilterViewHolder =
        ChooseFilterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filter_bottom_sheet, parent, false)
        ).also {
            it.mSelectFilter = mSelectFilter
        }

    override fun onBindViewHolder(holder: ChooseFilterViewHolder, position: Int) {
        holder.bind(filters[position])
    }

    override fun getItemCount(): Int = filters.size

    fun updateList(filters: List<String>) {
        this.filters.clear()
        this.filters.addAll(filters)
        notifyDataSetChanged()
    }
}