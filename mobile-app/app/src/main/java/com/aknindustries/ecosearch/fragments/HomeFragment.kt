package com.aknindustries.ecosearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.MainActivity
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.FragmentHomeBinding
import com.aknindustries.ecosearch.models.Record

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.setSupportActionBarTitle(resources.getString(R.string.home_title))
        fetchRecords()
    }

    private fun fetchRecords() {
        showProgressDialog()
        Records(requireContext()).fetchRecords(this)
    }

    fun fetchRecordsSuccess(records: ArrayList<Record>) {
        hideProgressDialog()
    }

    fun fetchRecordsFailure(errorMessage: String) {
        hideProgressDialog()
    }
}