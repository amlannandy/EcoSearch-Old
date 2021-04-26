package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.view.View
import com.aknindustries.ecosearch.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarProfileActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarProfileActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {

    }
}