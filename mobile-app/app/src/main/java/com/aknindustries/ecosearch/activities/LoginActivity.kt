package com.aknindustries.ecosearch.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginGoToRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.login_go_to_register -> goToRegister()
                R.id.tv_forgot_password -> goToForgotPassword()
                R.id.btn_login -> loginHandler()
            }
        }
    }

    private fun goToRegister() {
        intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToForgotPassword() {
        intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun loginHandler() {
        val email = binding.loginEmailEt.text.toString().trim()
        val password = binding.loginPasswordEt.text.toString().trim()
        if (validateLogin(email, password)) {
            Auth(applicationContext).login(email, password)
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                return false
            }
            TextUtils.isEmpty(password) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }
            else -> true
        }
    }

}