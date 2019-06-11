package com.param.tinynews

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.Manifest.permission
import androidx.core.app.ActivityCompat
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


open class BaseActivity : AppCompatActivity() {

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



    open fun openImageChooserDialog() {
        run {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Image From")
            builder.setItems(
                arrayOf<CharSequence>("Gallery", "Camera")
            ) { dialog, pos ->
                when (pos) {
                    0 -> {

                        if(!hasPermissionForExternalStorage()){
                            requestPermissionForExternalStorage(EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_GALLERY)
                        } else {
                        // GET IMAGE FROM THE GALLERY
                                val intent = Intent(Intent.ACTION_GET_CONTENT )
                                intent.type = "image/*"
                                 val chooser = Intent.createChooser(intent, "Choose a Picture")
                                startActivityForResult(chooser, RESULT_LOAD_IMAGE)
                            }
                    }

                    1 -> {

                        if (!hasPermissionForCamera()) {
                            requestPermissionForCamera()
                        } else {

                            dispatchTakePictureIntent()
                        }
                    }
                    else -> {
                    }
                }
            }

            builder.show()
            return
        }
    }


        val REQUEST_IMAGE_CAPTURE = 1

        private fun dispatchTakePictureIntent() {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }


    fun hasPermissionForExternalStorage(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun hasPermissionForCamera(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkLocationPermission(): Boolean {

        val result = ActivityCompat.checkSelfPermission(applicationContext, permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionForExternalStorage(requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(
                applicationContext,
                "External Storage permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        }
    }

    fun requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.CAMERA)) {
            Toast.makeText(
                this.applicationContext,
                "Camera permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    fun requestPermissionForLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Toast.makeText(
                applicationContext,
                "Location permission needed. Please allow in App Settings for additional functionality.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            /***
             * if user has granted permission then should open gallery
             */
            EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_LOAD_PROFILE ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  //permission granted successfully

            }

            CAMERA_PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent()
            }



        }
    }



}