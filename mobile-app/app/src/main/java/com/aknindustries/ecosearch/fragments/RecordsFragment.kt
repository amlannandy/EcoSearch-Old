package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.AddRecordActivity
import com.aknindustries.ecosearch.activities.MainActivity
import com.aknindustries.ecosearch.adaptors.UserRecordsAdaptor
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.FragmentRecordsBinding
import com.aknindustries.ecosearch.models.Record

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

    fun fetchUserProductsSuccess(records: ArrayList<Record>) {
        hideProgressDialog()
        if (records.isNotEmpty()) {
            val userRecordsAdaptor = UserRecordsAdaptor(requireContext(), records, this)
            binding.fragmentRecordsRecyclerView.setHasFixedSize(true)
            binding.fragmentRecordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.fragmentRecordsRecyclerView.adapter = userRecordsAdaptor
        } else {
            TODO("Add empty message")
        }
    }

    fun fetchUserProductsFailure(errorMessage: String) {
        hideProgressDialog()
        Log.d("Error", errorMessage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.records_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_record -> goToAddRecord()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToAddRecord() {
        val intent = Intent(activity, AddRecordActivity::class.java)
        startActivity(intent)
    }
}