package com.example.araccessories.ui.features.augmentedImage

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentAugmentedImageBinding
import com.example.araccessories.databinding.FragmentSplashBinding
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment


class AugmentedImageFragment : ArFragment() {
    private lateinit var binding: FragmentAugmentedImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAugmentedImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun getSessionConfiguration(session: Session?): Config {
        var config: Config = Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        config.focusMode = Config.FocusMode.AUTO
        session?.configure(config)
        this.arSceneView.setupSession(session)
        if (session != null) {
            setupAugmentedImage(config,session)
        }

                return config
    }
    private fun setupAugmentedImage(config: Config , session: Session){
        var qrCodeBitmap = BitmapFactory.decodeResource(resources,R.drawable.qrcode)
        var augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("qrcode",qrCodeBitmap)
        config.augmentedImageDatabase = augmentedImageDatabase
    }
}