package com.example.araccessories.ui.features.shoeTryOn

import android.hardware.camera2.params.SessionConfiguration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.example.araccessories.R
import com.example.araccessories.ui.core.arSession.CustomArFragment
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_shoe_try_on.ar_fragment

class ShoeTryOnActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var modelRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoe_try_on)
        arFragment =this.ar_fragment as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, _: Plane?, _: MotionEvent? ->
            if (modelRenderable == null) {
                ModelRenderable.builder()
                    .setSource(this, Uri.parse("yellow_sunglasses.sfb"))
                    .build()
                    .thenAccept { renderable ->
                        modelRenderable = renderable
                        placeModel(hitResult.createAnchor(), renderable)
                    }
            } else {
                placeModel(hitResult.createAnchor(), modelRenderable!!)
            }
        }



    }
    private fun placeModel(anchor: Anchor, modelRenderable: ModelRenderable) {
        val anchorNode = AnchorNode(anchor)
        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.setParent(anchorNode)
        transformableNode.renderable = modelRenderable
        arFragment.arSceneView.scene.addChild(anchorNode)
        transformableNode.select()
    }


}