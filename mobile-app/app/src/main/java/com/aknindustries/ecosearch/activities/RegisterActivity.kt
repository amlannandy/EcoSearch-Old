package com.aknindustries.ecosearch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.aknindustries.ecosearch.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view : ConstraintLayout = binding.root
        setContentView(view)

        setupActionBar()
        binding.goToLogin.setOnClickListener{ onBackPressed() }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
    }

}