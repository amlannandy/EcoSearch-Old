package com.aknindustries.ecosearch.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.activities.AddRecordActivity
import com.aknindustries.ecosearch.activities.MainActivity

class RecordsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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