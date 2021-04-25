package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view : ConstraintLayout = binding.root
        setContentView(view)

        setupActionBar()
        binding.goToLogin.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarRegisterActivity.setNavigationOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.go_to_login -> onBackPressed()
                R.id.btn_register -> registrationHandler()
            }
        }
    }

    private fun registrationHandler() {
        if (validateRegistration()) {
            showSnackBar("Registration Valid", false)
        }
    }

    private fun validateRegistration() : Boolean {
        val firstName = binding.registerEtFirstName.text.toString().trim()
        val lastName = binding.registerEtLastName.text.toString().trim()
        val email = binding.registerEtEmail.text.toString().trim()
        val username = binding.registerEtUsername.text.toString().trim()
        val password = binding.registerEtPassword.text.toString().trim()
        val confirmPassword = binding.registerEtConfirmPassword.text.toString().trim()
        val termsAndConditions = binding.registerCbTermsAndCondition.isChecked
        return when {
            TextUtils.isEmpty(firstName) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(lastName) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }
            TextUtils.isEmpty(email) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(username) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_username), true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            TextUtils.isEmpty(confirmPassword) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            // Check for valid email
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }

            password != confirmPassword -> {
                showSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !termsAndConditions -> {
                showSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> true
        }
    }
}