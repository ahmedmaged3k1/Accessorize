package com.example.araccessories.data.dataSource.localDataSource.sharedPrefrence

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    companion object {
        private var sharedPref: SharedPreferences? = null

        fun init(context: Context) {
            if (sharedPref == null)
                sharedPref =
                    context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }

        fun readStringFromSharedPreference(key: String, defValue: String) =
            sharedPref?.let { sharedPref ->
                defValue.let { value ->
                    sharedPref.getString(key, value)
                }
            }

        fun writeStringFromSharedPreference(key: String, value: String) {
            sharedPref?.edit()?.let { prefsEditor ->
                value.let { value ->
                    prefsEditor.putString(key, value)
                    prefsEditor.apply()
                }
            }
        }
    }
}