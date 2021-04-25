package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.view.View
import com.aknindustries.ecosearch.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {

        }
    }


}