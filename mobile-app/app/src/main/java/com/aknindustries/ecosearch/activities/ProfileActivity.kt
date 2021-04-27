package com.aknindustries.ecosearch.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Auth
import com.aknindustries.ecosearch.databinding.ActivityProfileBinding
import com.aknindustries.ecosearch.utils.Constants

class ProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        fetchUserDetails()
        binding.btnUpdateProfile.setOnClickListener(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarProfileActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarProfileActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun fetchUserDetails() {
        val user = Auth(applicationContext).getUserFromLocalStorage(this)
        if (user != null) {
            binding.profileEtName.setText(user.name)
            binding.profileEtUsername.setText(user.username)
            binding.profileEtEmail.setText(user.email)
            binding.profileEtEmail.isEnabled = false
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_update_profile -> updateProfile()
            }
        }
    }

    private fun updateProfile() {
        val name = binding.profileEtName.text.toString().trim()
        val username = binding.profileEtUsername.text.toString().trim()
        if (validateUpdateProfile(name, username)) {
            showProgressDialog()

            // Create HashMap
            val putData = HashMap<String, String>()
            putData[Constants.NAME] = name
            putData[Constants.USERNAME] = username

            Auth(applicationContext).updateInfo(this, putData)
        }
    }

    private fun validateUpdateProfile(name: String, username: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_name), true)
                false
            }
            TextUtils.isEmpty(username) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_username), true)
                false
            }
            else -> true
        }
    }

    fun updateProfileSuccess() {
        hideProgressDialog()
        showSnackBar(resources.getString(R.string.profile_update_success), false)
        finish()
    }

    fun updateProfileFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }
}