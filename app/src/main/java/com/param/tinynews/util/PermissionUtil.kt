package com.param.tinynews.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.param.tinynews.BaseActivity

class PermissionUtil(var activity:Activity){


    companion object {

        val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_GALLERY = 0
        val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_CAMERA = 1
        val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_LOAD_PROFILE = 2
        val CAMERA_PERMISSION_REQUEST_CODE = 3
        val LOCATION_PERMISSION_REQUEST_CODE = 4
        val PICK_PHOTO_FOR_AVATAR = 5
        val RESULT_LOAD_IMAGE = 6
        val CAMERA_REQUEST = 7

    }

    fun checkPermissionForExternalStorage(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForCamera(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkLocationPermission(): Boolean {

        val result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionForExternalStorage(requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(
                activity,
                "External Storage permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        }
    }

    fun requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(
                this.activity,
                "Camera permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                BaseActivity.CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    fun requestPermissionForLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Toast.makeText(
                activity,
                "Location permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                BaseActivity.LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

}


