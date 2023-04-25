package com.example.araccessories.ui.features.makeUpTryOn

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.coroutines.launch

class MakeUpTryOnViewModel : ViewModel() {
    private var isDepthSupported = false
    private lateinit var sceneView: ArSceneView
    private lateinit var scene: Scene
    private lateinit var config: Config

    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var faceMeshTexture: Texture? = null
    private fun configureArSession(productModel: ModelRenderable?, arFragment: ArFragment) {
        sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        scene = sceneView.scene

    }

    private fun enableDepth() {
        config = sceneView.session!!.config
        isDepthSupported =
            sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
        if (isDepthSupported) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC)
        } else {
            config.setDepthMode(Config.DepthMode.DISABLED)
        }
        sceneView.session!!.configure(config)

    }

    fun tryOnProduct(
        productModel: ModelRenderable?,
        arFragment: ArFragment,
        context: Context,
        productImage: Int
    ) {
        configureArSession(productModel, arFragment)
        scene.addOnUpdateListener {
            Texture.builder()
                .setSource(context, productImage)
                .build()
                .thenAccept { texture -> faceMeshTexture = texture }
            faceMeshTexture.let {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        enableDepth()
                        for (face in it) {
                            if (!faceNodeMap.containsKey(face)) {
                                attachModel(face)

                            }
                        }
                        removeRedundantModels()
                    }
            }


        }
    }

    private fun attachModel(face: AugmentedFace) {

        val faceNode = AugmentedFaceNode(face)
        faceNode.setParent(scene)
        faceNode.faceMeshTexture = faceMeshTexture
        faceNodeMap[face] = faceNode
    }

    private fun removeRedundantModels() {
        // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
        val inter = faceNodeMap.entries.iterator()
        while (inter.hasNext()) {
            val entry = inter.next()
            val face = entry.key
            if (face.trackingState == TrackingState.STOPPED) {
                val faceNode = entry.value
                faceNode.setParent(null)
                inter.remove()
            }
        }
    }

    fun takeSnapShot(context: Context) {
        viewModelScope.launch {
            val width = sceneView.width
            val height = sceneView.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            PixelCopy.request(sceneView, bitmap, { res ->
                val filename = "Try On_${System.currentTimeMillis()}.png"
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                }
                val contentResolver = context.contentResolver
                val uri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                uri?.let {
                    contentResolver.openOutputStream(it).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        Toast.makeText(context, "Photo saved to gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            }, Handler(Looper.getMainLooper()))
        }


    }
}