package com.example.araccessories.ui.features.augmentedImage

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Display.Mode
import androidx.appcompat.app.AppCompatActivity
import com.example.araccessories.R
import com.example.araccessories.databinding.ActivityAugmentedImageBinding
import com.google.ar.core.*
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import org.opencv.dnn.Model

class AugmentedImageActivity : AppCompatActivity(), Scene.OnUpdateListener {
    private lateinit var binding: ActivityAugmentedImageBinding
    private lateinit var augmentedImageFragment :AugmentedImageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAugmentedImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        augmentedImageFragment = supportFragmentManager.findFragmentById(R.id.fragment) as AugmentedImageFragment
        augmentedImageFragment.arSceneView.scene.addOnUpdateListener(this)

    }
    public fun setupAugmentedImage(config: Config , session: Session){
        var qrCodeBitmap = BitmapFactory.decodeResource(resources,R.drawable.qrcode)
         var augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("qrcode",qrCodeBitmap)
        config.augmentedImageDatabase = augmentedImageDatabase
    }

    override fun onUpdate(p0: FrameTime?) {
        val frame = augmentedImageFragment.arSceneView.arFrame
        var images  = frame?.getUpdatedTrackables(AugmentedImage::class.java) as Collection<AugmentedImage>
        for (image in images)
        {
            if (image.trackingState==TrackingState.TRACKING)
            {
                if(image.name.equals("qrcode"))
                {
                    var anchor = image.createAnchor(image.centerPose)
                    createModel(anchor)
                }
            }
        }
    }

    private fun createModel(anchor: Anchor) {
        ModelRenderable.builder().setSource(this,Uri.parse("tshirt.sfb"))
            .build().thenAccept {
                placeModel(it,anchor)
            }
    }

    private fun placeModel(modelRenderable: ModelRenderable?, anchor: Anchor) {

    }


}