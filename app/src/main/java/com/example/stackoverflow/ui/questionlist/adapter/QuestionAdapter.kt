package com.example.stackoverflow.ui.questionlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflow.R
import com.example.stackoverflow.data.model.Banner
import com.example.stackoverflow.data.model.Question
import com.example.stackoverflow.data.model.WidgetData
import com.example.stackoverflow.ui.base.BaseViewHolder
import com.example.stackoverflow.ui.questionlist.BannerWidgetListener
import com.example.stackoverflow.ui.questionlist.viewholder.BannerViewHolder
import com.example.stackoverflow.ui.questionlist.viewholder.QuestionViewHolder
import java.util.*

class QuestionAdapter(private val mBannerWidgetListener: BannerWidgetListener) : RecyclerView.Adapter<BaseViewHolder<out WidgetData>>()  {

    companion object {
        const val VIEW_TYPE_BANNER = 1
        const val VIEW_TYPE_QUESTION = 2
    }

    private val widgetData = mutableListOf<WidgetData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out WidgetData> {
        return when (viewType) {
            VIEW_TYPE_QUESTION -> {
                QuestionViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_question_view, parent, false)
                )
            }
            else -> BannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_banner, parent, false)
            ).also {
                it.mBannerWidgetListener = mBannerWidgetListener
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out WidgetData>, position: Int) {
        when (widgetData[position]){
            is Banner -> {
                (holder as BannerViewHolder)
                    .bind((widgetData[position] as Banner).also {
                        it.position = position
                    })
            }
            is Question -> {(holder as QuestionViewHolder)
                .bind(widgetData[position] as Question)
                }
        }
    }

    override fun getItemCount(): Int = widgetData.size

    override fun getItemViewType(position: Int): Int {
        return when(widgetData[position]){
            is Question -> VIEW_TYPE_QUESTION
            is Banner -> VIEW_TYPE_BANNER
        }
    }

    fun updateList(widgetData: List<WidgetData>) {
        this.widgetData.clear()
        this.widgetData.addAll(widgetData)
        notifyDataSetChanged()
    }
}