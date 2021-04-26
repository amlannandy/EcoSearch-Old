package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.ActivityUpdatePasswordBinding
import com.aknindustries.ecosearch.utils.Constants

class UpdatePasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUpdatePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        binding.btnUpdatePassword.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarUpdatePasswordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarUpdatePasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_update_password -> updatePassword()
            }
        }
    }

    private fun updatePassword() {
        if (validateUpdatePassword()) {
            showProgressDialog()
            val currentPassword = binding.updatePasswordEtCurrentPassword.text.toString().trim()
            val newPassword = binding.updatePasswordEtNewPassword.text.toString().trim()
            val postData = HashMap<String, String>()
            postData[Constants.CURRENT_PASSWORD] = currentPassword
            postData[Constants.NEW_PASSWORD] = newPassword
            Auth(applicationContext).updatePassword(this, postData)
        }
    }

    fun updatePasswordSuccess(successMessage: String) {
        hideProgressDialog()
        showSnackBar(successMessage, false)
        onBackPressed()
    }

    fun updatePasswordFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }

    private fun validateUpdatePassword(): Boolean {
        val currentPassword = binding.updatePasswordEtCurrentPassword.text.toString().trim()
        val newPassword = binding.updatePasswordEtNewPassword.text.toString().trim()
        val confirmNewPassword = binding.updatePasswordEtConfirmNewPassword.text.toString().trim()
        return when {
            TextUtils.isEmpty(currentPassword) -> {
                showSnackBar(resources.getString(R.string.current_password_err), true)
                false
            }
            TextUtils.isEmpty(newPassword) -> {
                showSnackBar(resources.getString(R.string.new_password_err), true)
                false
            }
            TextUtils.isEmpty(confirmNewPassword) -> {
                showSnackBar(resources.getString(R.string.confirm_new_password_err), true)
                false
            }
            newPassword != confirmNewPassword -> {
                showSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            else -> true
        }
    }

}