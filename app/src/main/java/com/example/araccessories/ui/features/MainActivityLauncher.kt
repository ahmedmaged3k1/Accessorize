package com.example.araccessories.ui.features

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.araccessories.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityLauncher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // Retrieve the current window flags
        val flags = window.attributes.flags

// Clear the full-screen flag
        window.attributes.flags = flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
// Set the desired status bar color
        val statusBarColor = ContextCompat.getColor(this, R.color.white)

// Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = statusBarColor
        }

    }
}