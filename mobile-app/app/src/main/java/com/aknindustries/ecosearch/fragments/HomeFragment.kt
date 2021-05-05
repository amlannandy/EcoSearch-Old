package com.aknindustries.ecosearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.MainActivity
import com.aknindustries.ecosearch.adaptors.ExploreItemsAdaptor
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.FragmentHomeBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.SpannedGridLayoutManager

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
        if (records.isNotEmpty()) {
            val userRecordsAdaptor = ExploreItemsAdaptor(requireContext(), records, this)
            binding.fragmentHomeRecyclerView.setHasFixedSize(true)
            binding.fragmentHomeRecyclerView.layoutManager = SpannedGridLayoutManager(
                object : SpannedGridLayoutManager.GridSpanLookup {
                    override fun getSpanInfo(position: Int): SpannedGridLayoutManager.SpanInfo {
                        // Conditions for 2x2 items
                        return if (position % 6 == 0 || position % 6 == 4) {
                            SpannedGridLayoutManager.SpanInfo(2, 2)
                        } else {
                            SpannedGridLayoutManager.SpanInfo(1, 1)
                        }
                    }
                },
                3,
                1f,
            )
            binding.fragmentHomeRecyclerView.adapter = userRecordsAdaptor
        } else {
            binding.fragmentHomeRecyclerView.visibility = View.INVISIBLE
            binding.fragmentHomeMessage.visibility = View.VISIBLE
            binding.fragmentHomeMessage.text = resources.getString(R.string.no_records_found)
        }
    }

    fun fetchRecordsFailure(errorMessage: String) {
        hideProgressDialog()
        binding.fragmentHomeRecyclerView.visibility = View.INVISIBLE
        binding.fragmentHomeMessage.visibility = View.VISIBLE
        binding.fragmentHomeMessage.text = errorMessage
    }
}