package com.aknindustries.ecosearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants

class RecordDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_details)

        fetchRecord()
    }

    private fun fetchRecord() {
        val id = intent.getIntExtra(Constants.RECORD_ID, -1)
        if (id != -1) {
            Records(applicationContext).fetchRecordById(this, id)
        }
    }

    fun fetchRecordSuccess(record: Record) {
        Log.d("Record", record.title)
    }

    fun fetchRecordFailure(errorMessage: String) {
        Log.d("Error", errorMessage)
    }
}