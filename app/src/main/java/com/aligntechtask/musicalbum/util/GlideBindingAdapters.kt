package com.aligntechtask.musicalbum.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.aligntechtask.musicalbum.R

object GlideBindingAdapters {
    @BindingAdapter("imageResource")
    fun setImageResource(view: ImageView, imageUrl: Int) {
        val context = view.context
        val option = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(option)
            .load(imageUrl)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("imageResource")
    fun setImageResource(view: ImageView, imageUrl: String?) {
        val context = view.context
        val option = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(context)
            .applyDefaultRequestOptions(option.transforms(CenterCrop(), RoundedCorners(16)))

            .load(imageUrl)
            .into(view)
    }
}