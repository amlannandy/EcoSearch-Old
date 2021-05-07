package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.ActivityRecordDetailsBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.GlideLoader

class RecordDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityRecordDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        fetchRecord()
    }

    private fun fetchRecord() {
        val id = intent.getIntExtra(Constants.RECORD_ID, -1)
        if (id != -1) {
            showProgressDialog()
            Records(applicationContext).fetchRecordById(this, id)
        }
    }

    fun fetchRecordSuccess(record: Record) {
        hideProgressDialog()
        GlideLoader(applicationContext).loadRecordImage(record.image, binding.recordDetailsImageView)
        binding.toolbarTitle.text = null
    }

    fun fetchRecordFailure(errorMessage: String) {
        hideProgressDialog()
        Log.d("Error", errorMessage)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRecordDetailsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarRecordDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.record_details_menu, menu)
        return true
    }
}