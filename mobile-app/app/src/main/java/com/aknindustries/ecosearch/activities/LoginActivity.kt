package com.aknindustries.ecosearch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginGoToRegister.setOnClickListener { goToRegister() }
    }

    private fun goToRegister() {
        intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
    }
}