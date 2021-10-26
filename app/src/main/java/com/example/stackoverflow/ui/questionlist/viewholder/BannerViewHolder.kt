package com.example.stackoverflow.ui.questionlist.viewholder

import android.view.View
import com.example.stackoverflow.data.model.Banner
import com.example.stackoverflow.databinding.ItemBannerBinding
import com.example.stackoverflow.ui.base.BaseViewHolder
import com.example.stackoverflow.ui.questionlist.BannerWidgetListener
import com.example.stackoverflow.utils.loadImage

class BannerViewHolder(itemView: View) : BaseViewHolder<Banner>(itemView){

    val binding = ItemBannerBinding.bind(itemView)
    var mBannerWidgetListener: BannerWidgetListener? = null

    override fun bind(data: Banner) {
        binding.ivClose.setOnClickListener {
            mBannerWidgetListener?.onClickCancel(data.position)
        }
        binding.ivBanner.loadImage(data.bannerImage)
    }

}