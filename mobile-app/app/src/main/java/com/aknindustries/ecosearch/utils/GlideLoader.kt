package com.aknindustries.ecosearch.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadRecordImage(image: Any, imageView: ImageView) {
        try {
            Glide.with(context).load(image)
                .centerCrop()
                .into(imageView)
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

}