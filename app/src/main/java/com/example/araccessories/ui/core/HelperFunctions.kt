package com.example.araccessories.ui.core

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.widget.Toast
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.google.ar.core.ArCoreApk
import dagger.hilt.android.internal.Contexts.getApplication

object HelperFunctions {
     fun checkIsSupportedDeviceOrFinish(context: Context?, activity : Activity?): Boolean {
        if (ArCoreApk.getInstance()
                .checkAvailability(context) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE
        ) {
            Toast.makeText(context, "Augmented Faces requires ARCore", Toast.LENGTH_LONG)
                .show()
            activity?.finish()
            return false
        }
        val openGlVersionString =
            (activity?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
                ?.deviceConfigurationInfo
                ?.glEsVersion

        openGlVersionString?.let {
            if (java.lang.Double.parseDouble(openGlVersionString) < GlassesActivity.MIN_OPENGL_VERSION) {
                Toast.makeText(
                    context,
                    "Scene-form requires OpenGL ES 3.0 or later",
                    Toast.LENGTH_LONG
                )
                    .show()
                activity.finish()

                return false
            }
        }
        return true
    }
    // function to check internet connectivity ( returns true when internet is reliable and it will return false if not



}