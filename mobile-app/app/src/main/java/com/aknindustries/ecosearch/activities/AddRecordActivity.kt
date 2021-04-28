package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.view.View
import com.aknindustries.ecosearch.databinding.ActivityAddRecordBinding

class AddRecordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddRecordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddRecordActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {

    }
}