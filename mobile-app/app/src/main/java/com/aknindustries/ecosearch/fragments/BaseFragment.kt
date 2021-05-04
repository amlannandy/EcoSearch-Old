package com.aknindustries.ecosearch.fragments

import android.app.Dialog
import android.graphics.LightingColorFilter
import androidx.fragment.app.Fragment
import com.aknindustries.ecosearch.R

open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog.window?.decorView?.background?.colorFilter = LightingColorFilter(
            0xFF000000.toInt(),
            0xFFffffff.toInt(),
        )
        mProgressDialog.setContentView(R.layout.progress_indicator)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}