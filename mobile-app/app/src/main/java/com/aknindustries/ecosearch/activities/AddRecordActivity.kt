package com.aknindustries.ecosearch.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.databinding.ActivityAddRecordBinding
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.GlideLoader
import java.io.IOException

class AddRecordActivity : BaseActivity(), View.OnClickListener {

    private var mImageUri: Uri? = null
    private lateinit var binding: ActivityAddRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar()
        binding.btnUseGallery.setOnClickListener(this)
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
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.USE_GALLERY_CODE) {
            Log.d("Grant", grantResults[0].toString())
            Log.d("Mine", PackageManager.PERMISSION_GRANTED.toString())
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.useGallery(this)
            } else {
                showSnackBar(resources.getString(R.string.use_gallery_permission_denied), true)
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
}