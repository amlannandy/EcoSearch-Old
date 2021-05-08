package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.ActivityEditRecordBinding
import com.aknindustries.ecosearch.utils.Constants

class EditRecordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        fetchRecord()

        binding.btnEditRecord.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_edit_record -> updateRecord()
            }
        }
    }

    private fun fetchRecord() {
        val description = intent.getStringExtra(Constants.DESCRIPTION)
        if (description != null) {
            binding.editRecordEtDescription.setText(description)
        }
    }

    private fun updateRecord() {
        val description = binding.editRecordEtDescription.text.toString().trim()
        if (description.isEmpty()) {
            showSnackBar(resources.getString(R.string.err_enter_description), true)
            return
        }
        val id = intent.getIntExtra(Constants.RECORD_ID, -1)
        if (id != -1) {
            showProgressDialog()
            Records(applicationContext).updateRecord(this, id, description)
        }
    }

    fun updateRecordSuccess() {
        hideProgressDialog()
        finish()
    }

    fun updateRecordFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarEditRecordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarEditRecordActivity.setNavigationOnClickListener { onBackPressed() }
    }

}