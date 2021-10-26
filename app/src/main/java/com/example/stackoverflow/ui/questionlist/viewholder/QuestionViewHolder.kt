package com.example.stackoverflow.ui.questionlist.viewholder

import android.view.View
import android.widget.Toast
import com.example.stackoverflow.R
import com.example.stackoverflow.data.model.Question
import com.example.stackoverflow.databinding.ItemQuestionViewBinding
import com.example.stackoverflow.ui.base.BaseViewHolder
import com.example.stackoverflow.utils.UtilFunctions
import com.example.stackoverflow.utils.openUrlInChrome
import com.example.stackoverflow.utils.loadImage
import com.example.stackoverflow.utils.setTheDate

class QuestionViewHolder(itemView: View) : BaseViewHolder<Question>(itemView) {

    private val binding by lazy { ItemQuestionViewBinding.bind(itemView) }

    override fun bind(data: Question) {
        binding.apply {
            val owner = data.owner
            ivUser.loadImage(owner.profileImage, R.drawable.ic_placeholder_user)
            tvQuestionText.text = data.title
            tvUserName.text = owner.displayName
            tvPostedTime.text = data.creationDate.setTheDate()
            root.setOnClickListener {
                if (UtilFunctions.isNetworkAvailable(it.context)) {
                    it.context.openUrlInChrome(data.link)
                } else {
                    Toast.makeText(
                        it.context,
                        it.context.getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}