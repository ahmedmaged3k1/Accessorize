package com.example.araccessories.ui.features.augmentedImage

import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class CustomArFragment : ArFragment() {
    override fun getSessionConfiguration(session: Session?): Config {
        var config: Config = Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        config.focusMode = Config.FocusMode.AUTO
        session?.configure(config)
        this.arSceneView.setupSession(session)
       var arActivity =  activity as     AugmentedImageActivity
        if (session != null) {
            arActivity.setupAugmentedImage(config,session)
        }
        return config
    }

}