package com.aknindustries.ecosearch.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.ActivityRecordDetailsBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.GlideLoader

class RecordDetailsActivity : BaseActivity() {

    private var mRecord: Record? = null
    private lateinit var binding: ActivityRecordDetailsBinding

    override fun onResume() {
        super.onResume()
        fetchRecord()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
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
        mRecord = record
        binding.toolbarTitle.text = null
        GlideLoader(applicationContext).loadRecordImage(record.image, binding.recordDetailsImageView)
        binding.recordDetailsTitle.text = record.title
        binding.recordDetailsDescription.text = record.description
        binding.recordDetailsType.text = record.type
        binding.recordDetailsLabel.text = when (record.label) {
            Constants.NULL -> resources.getString(R.string.unknown)
            else -> record.label
        }
    }

    fun fetchRecordFailure(errorMessage: String) {
        hideProgressDialog()
        Log.d("Error", errorMessage)
    }

    private fun goToEditRecord() {
        if (mRecord != null) {
            intent = Intent(this@RecordDetailsActivity, EditRecordActivity::class.java)
            intent.putExtra(Constants.RECORD_ID, mRecord!!.id)
            intent.putExtra(Constants.DESCRIPTION, mRecord!!.description)
            startActivity(intent)
        }
    }

    private fun deleteRecordConfirmation() {
        val id = intent.getIntExtra(Constants.RECORD_ID, -1)
        if (id == -1)
            return
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.delete_record_dialog_title))
        builder.setMessage(resources.getString(R.string.delete_record_dialog))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // Yes
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            deleteRecord(id)
        }
        // No
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteRecord(id: Int) {
        showProgressDialog()
        Records(applicationContext).deleteRecord(this, id)
    }

    fun deleteRecordSuccess() {
        hideProgressDialog()
        finish()
    }

    fun deleteRecordFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_record -> goToEditRecord()
            R.id.action_delete_record -> deleteRecordConfirmation()
        }
        return super.onOptionsItemSelected(item)
    }
}