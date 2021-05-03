package com.aknindustries.ecosearch.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.ActivityAddRecordBinding
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.GlideLoader
import java.io.IOException

class AddRecordActivity : BaseActivity(), View.OnClickListener {

    private var mImageUri: Uri? = null
    private var mLocation: Location? = null
    private lateinit var binding: ActivityAddRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        binding.btnUseGallery.setOnClickListener(this)
        binding.btnAddRecord.setOnClickListener(this)

        getCurrentLocation()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddRecordActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddRecordActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_use_gallery -> {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Constants.useGallery(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.USE_GALLERY_CODE,
                        )
                    }
                }
                R.id.btn_add_record -> addRecord()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.USE_GALLERY_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.useGallery(this)
            } else {
                showSnackBar(resources.getString(R.string.use_gallery_permission_denied), true)
            }
        } else if (requestCode == Constants.LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocation = Constants.getCurrentLocation(this)
            } else {
                showSnackBar(resources.getString(R.string.use_location_permission_denied), true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.USE_GALLERY_CODE) {
                if (data != null) {
                    try {
                        mImageUri = data.data!!
                        GlideLoader(this).loadRecordImage(mImageUri!!, binding.recordImageView)
                    } catch (e : IOException) {
                        e.printStackTrace()
                        showSnackBar(resources.getString(R.string.image_selection_failure), true)
                    }
                }
            }
        }
    }

    private fun addRecord() {
        val title = binding.addRecordEtTitle.text.toString().trim()
        val description = binding.addRecordEtDescription.text.toString().trim()
        val type = when (binding.addRecordCgType.checkedChipId) {
            R.id.chip_animal -> "animal"
            R.id.chip_bird -> "bird"
            R.id.chip_plant -> "plant"
            R.id.chip_insect -> "insect"
            else -> "unknown"
        }
        if (validateAddRecord(title, description)) {
            showProgressDialog()
            val postData = HashMap<String, Any?>()
            postData[Constants.TITLE] = title
            postData[Constants.DESCRIPTION] = description
            postData[Constants.TYPE] = type
            postData[Constants.LATITUDE] = mLocation?.latitude
            postData[Constants.LONGITUDE] = mLocation?.longitude
            Records(applicationContext).addRecord(this, postData, mImageUri!!)
        }
    }

    fun addRecordSuccess() {
        hideProgressDialog()
        showSnackBar(resources.getString(R.string.add_record_success), false)
        finish()
    }

    fun addRecordFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }

    private fun validateAddRecord(title: String, description: String): Boolean {
        return when {
            TextUtils.isEmpty(title) -> {
                showSnackBar(resources.getString(R.string.err_enter_title), true)
                return false
            }
            TextUtils.isEmpty(description) -> {
                showSnackBar(resources.getString(R.string.err_enter_description), true)
                return false
            }
            mImageUri == null -> {
                showSnackBar(resources.getString(R.string.err_upload_record_image), true)
                return false
            }
            mLocation == null -> {
                showSnackBar(resources.getString(R.string.err_need_location), true)
                return false
            }
            else -> true
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocation = Constants.getCurrentLocation(this)
            Log.d("Coordinates", "(${mLocation?.latitude}, ${mLocation?.longitude})")
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.LOCATION_PERMISSION_CODE,
            )
        }
    }
}