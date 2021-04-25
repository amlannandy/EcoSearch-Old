package com.aknindustries.ecosearch.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadUser()
    }

    private fun loadUser() {
        Auth(applicationContext).getCurrentUser(this)
    }

    fun loadLogin() {
        intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadHome() {
        intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}