package com.aknindustries.ecosearch.utils

import android.content.Context
import android.widget.ImageView
import com.aknindustries.ecosearch.R
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadRecordImage(image: Any, imageView: ImageView) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(imageView)
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

}