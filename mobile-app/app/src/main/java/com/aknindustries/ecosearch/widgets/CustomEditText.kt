package com.aknindustries.ecosearch.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText (context: Context, attributeSet: AttributeSet) : AppCompatEditText(context, attributeSet) {

    init {
        applyFont()
    }

    private fun applyFont() {
        // Get font file from assets folder and set it in text view
        val typeface : Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }

}