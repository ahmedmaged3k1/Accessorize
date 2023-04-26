package com.example.araccessories.ui.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.araccessories.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityLauncher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}