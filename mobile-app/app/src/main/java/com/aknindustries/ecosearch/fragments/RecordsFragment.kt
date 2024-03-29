package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.AddRecordActivity
import com.aknindustries.ecosearch.activities.MainActivity
import com.aknindustries.ecosearch.activities.MapsActivity
import com.aknindustries.ecosearch.activities.RecordDetailsActivity
import com.aknindustries.ecosearch.adaptors.UserRecordsAdaptor
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.FragmentRecordsBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants

class RecordsFragment : BaseFragment() {

    private var _binding: FragmentRecordsBinding? = null
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
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.setSupportActionBarTitle(resources.getString(R.string.records_title))
        fetchUserRecords()
    }

    private fun fetchUserRecords() {
        showProgressDialog()
        Records(requireActivity().applicationContext).fetchUserRecords(this)
    }

    fun fetchUserRecordsSuccess(records: ArrayList<Record>) {
        hideProgressDialog()
        if (records.isNotEmpty()) {
            val userRecordsAdaptor = UserRecordsAdaptor(requireContext(), records, this)
            binding.fragmentRecordsRecyclerView.setHasFixedSize(true)
            binding.fragmentRecordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.fragmentRecordsRecyclerView.adapter = userRecordsAdaptor
        } else {
            binding.fragmentRecordsRecyclerView.visibility = View.INVISIBLE
            binding.fragmentRecordsMessage.visibility = View.VISIBLE
            binding.fragmentRecordsMessage.text = resources.getString(R.string.no_records_found)
        }
    }

    fun fetchUserRecordsFailure(errorMessage: String) {
        hideProgressDialog()
        binding.fragmentRecordsRecyclerView.visibility = View.INVISIBLE
        binding.fragmentRecordsMessage.visibility = View.VISIBLE
        binding.fragmentRecordsMessage.text = errorMessage
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.records_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_record -> goToAddRecord()
            R.id.action_view_map -> goToMapView()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToMapView() {
        val intent = Intent(activity, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun goToAddRecord() {
        val intent = Intent(activity, AddRecordActivity::class.java)
        startActivity(intent)
    }

    fun goToRecordDetails(id: Int) {
        val intent = Intent(activity, RecordDetailsActivity::class.java)
        intent.putExtra(Constants.RECORD_ID, id)
        startActivity(intent)
    }
}