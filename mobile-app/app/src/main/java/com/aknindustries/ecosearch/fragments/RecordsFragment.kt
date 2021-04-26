package com.aknindustries.ecosearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.MainActivity

class RecordsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_records, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.setSupportActionBarTitle(resources.getString(R.string.records_title))
    }
}