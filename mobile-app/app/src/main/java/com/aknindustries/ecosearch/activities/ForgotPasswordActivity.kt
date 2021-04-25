package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        binding.btnSendMail.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_send_mail -> sendPasswordResetMail()
            }
        }
    }

    private fun sendPasswordResetMail() {
        val email = binding.forgotPasswordEtEmail.text.toString().trim()
        if (validateEmail(email)) {
            showProgressDialog()
            Auth(applicationContext).sendPasswordResetEmail(this, email)
        }
    }

    fun passwordResetSuccess(successMessage: String) {
        hideProgressDialog()
        showSnackBar(successMessage, false)
    }

    fun passwordResetFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            // Check for valid email
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }
            else -> true
        }
    }


}