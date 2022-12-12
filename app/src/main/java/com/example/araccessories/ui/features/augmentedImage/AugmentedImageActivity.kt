package com.example.araccessories.ui.features.augmentedImage

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.araccessories.R
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable


class AugmentedImageActivity : AppCompatActivity(), Scene.OnUpdateListener {

    private lateinit var augmentedImageFragment: CustomArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented_image)
        augmentedImageFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as CustomArFragment
        augmentedImageFragment.arSceneView?.scene?.addOnUpdateListener(this)

    }

    public fun setupAugmentedImage(config: Config, session: Session) {
        var qrCodeBitmap = BitmapFactory.decodeResource(resources, R.drawable.qrcode)
        var augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("qrcode", qrCodeBitmap)
        config.augmentedImageDatabase = augmentedImageDatabase
    }

    override fun onUpdate(p0: FrameTime?) {
        val frame = augmentedImageFragment.arSceneView?.arFrame
        var images =
            frame?.getUpdatedTrackables(AugmentedImage::class.java) as Collection<AugmentedImage>
        for (image in images) {
            if (image.trackingState == TrackingState.TRACKING) {
                if (image.name.equals("qrcode")) {
                    var anchor = image.createAnchor(image.centerPose)
                    createModel(anchor)
                }
            }
        }
    }

    private fun createModel(anchor: Anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("shady2.sfb"))
            .build().thenAccept {
                placeModel(it, anchor)
            }
    }

    private fun placeModel(modelRenderable: ModelRenderable?, anchor: Anchor) {
        var anchorNode = AnchorNode(anchor)
        anchorNode.renderable = modelRenderable
        augmentedImageFragment.arSceneView?.scene?.addChild(anchorNode)
    }


}