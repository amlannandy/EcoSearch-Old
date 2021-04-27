package com.aknindustries.ecosearch.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.ActivityDeleteAccountBinding

class DeleteAccountActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDeleteAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        binding.btnDeleteAccount.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarDeleteAccountActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarDeleteAccountActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_delete_account -> deleteAccountConfirmation()
            }
        }
    }

    private fun deleteAccountConfirmation() {
        val password = binding.deleteAccountEtPassword.text.toString().trim()
        if (password.isEmpty()) {
            showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.delete_account_dialog_title))
        builder.setMessage(resources.getString(R.string.delete_account_dialog))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // Yes
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            deleteAccount(password)
        }
        // No
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteAccount(password: String) {
        showProgressDialog()
        Auth(applicationContext).deleteAccount(this, password)
    }

    fun deleteAccountSuccess(successMessage: String) {
        hideProgressDialog()
        val intent = Intent(this@DeleteAccountActivity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
        showSnackBar(successMessage, false)
    }

    fun deleteAccountFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }
}