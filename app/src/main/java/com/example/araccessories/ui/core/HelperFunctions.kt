package com.example.araccessories.ui.core

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.google.ar.core.ArCoreApk
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object      HelperFunctions {
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
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
     fun noInternetConnection(context : Context, activity: Activity){

         MotionToast.darkToast(
             activity,
             duration = 13000L,
             position = MotionToast.GRAVITY_BOTTOM,
             font = ResourcesCompat.getFont(
                 context,
                 www.sanju.motiontoast.R.font.helvetica_regular
             ),
             style = MotionToastStyle.NO_INTERNET,
             message = "No Internet Connection , Please Restart The App ",
             title = "Sorry"
         )
     }



}