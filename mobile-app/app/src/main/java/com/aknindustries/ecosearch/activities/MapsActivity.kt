package com.aknindustries.ecosearch.activities

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aknindustries.ecosearch.R
import com.aknindustries.ecosearch.api.Records
import com.aknindustries.ecosearch.databinding.ActivityMapsBinding
import com.aknindustries.ecosearch.models.Record
import com.aknindustries.ecosearch.utils.Constants
import com.aknindustries.ecosearch.utils.CustomBitmapDescriptor

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupActionBar()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestFineLocationPermission()
    }

    private fun requestFineLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fetchRecords()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.LOCATION_PERMISSION_CODE,
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchRecords()
            } else {
                showSnackBar(resources.getString(R.string.use_location_permission_denied), true)
            }
        }
    }

    private fun fetchRecords() {
        val fetchAll = intent.getBooleanExtra(Constants.FETCH_ALL, false)
        showProgressDialog()
        if (fetchAll) {
            Records(applicationContext).fetchRecords(this)
        } else {
            Records(applicationContext).fetchUserRecords(this)
        }
    }

    fun fetchRecordsSuccess(records: ArrayList<Record>) {
        hideProgressDialog()
        for (record in records) {
            val location = LatLng(record.location.latitude, record.location.longitude)
            mMap.addMarker(MarkerOptions()
                .position(location)
                .title(record.title)
                .icon(CustomBitmapDescriptor().bitmapDescriptorFromVector(
                    applicationContext,
                    when (record.type) {
                        Constants.ANIMAL -> R.drawable.ic_animal
                        Constants.BIRD -> R.drawable.ic_bird
                        Constants.PLANT -> R.drawable.ic_plant
                        Constants.INSECT -> R.drawable.ic_insect
                        else -> R.drawable.ic_baseline_location_on_24
                    }
                ))
            )
        }
        val currentLocation = Constants.getCurrentLocation(this)!!
        val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20F))
    }

    fun fetchRecordsFailure(errorMessage: String) {
        hideProgressDialog()
        showSnackBar(errorMessage, true)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMapsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMapsActivity.setNavigationOnClickListener { onBackPressed() }
    }
}