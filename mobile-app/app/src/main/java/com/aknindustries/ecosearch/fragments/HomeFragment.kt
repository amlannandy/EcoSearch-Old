package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.MainActivity
import com.aknindustries.ecosearch.activities.MapsActivity
import com.aknindustries.ecosearch.activities.RecordDetailsActivity
import com.aknindustries.ecosearch.adaptors.ExploreItemsAdaptor
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.FragmentHomeBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.SpannedGridLayoutManager

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view_map -> goToMapView()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToMapView() {
        val intent = Intent(activity, MapsActivity::class.java)
        intent.putExtra(Constants.FETCH_ALL, true)
        startActivity(intent)
    }

    fun goToRecordDetails(id: Int) {
        val intent = Intent(activity, RecordDetailsActivity::class.java)
        intent.putExtra(Constants.RECORD_ID, id)
        startActivity(intent)
    }
}