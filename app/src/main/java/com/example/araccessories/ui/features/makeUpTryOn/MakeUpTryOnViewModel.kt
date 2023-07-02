package com.example.araccessories.ui.features.makeUpTryOn

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.PixelCopy
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.araccessories.R
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
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

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
        productImage: String?
    ) {
        configureArSession(productModel, arFragment)
        Log.d(TAG, "tryOnProduct: $productImage")
        scene.addOnUpdateListener {
          /* Texture.builder()
                .setSource(context, R.drawable.face_removebg_preview)
                .build()
                .thenAccept { texture ->
                    
                    faceMeshTexture = texture }
*/

           Glide.with(context)
                .asBitmap()
                .load(productImage)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        Texture.builder()
                                //resource
                            .setSource(resource)
                            .build()
                            .thenAccept { texture -> faceMeshTexture = texture }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            faceMeshTexture.let {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        enableDepth()
                        for (face in it) {
                            if (!faceNodeMap.containsKey(face)) {
                                attachModel(face,context)
                            }
                        }
                        removeRedundantModels()
                    }
            }


        }
    }

    private fun attachModel(face: AugmentedFace, context: Context) {

        val faceNode = AugmentedFaceNode(face)
        faceNode.setParent(scene)
        faceNode.faceMeshTexture = faceMeshTexture

        ModelRenderable.builder()
            .setSource(context, Uri.parse("canonical_face_mesh"))
            .build()
            .thenAccept {
                it.isShadowCaster = false
                it.isShadowReceiver = false
                faceNode.faceRegionsRenderable =it
            }
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

    fun takeSnapShot(context : Context,activity: Activity) {
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

                        MotionToast.darkToast(
                            activity,
                            duration = 13000L,
                            position = MotionToast.GRAVITY_BOTTOM,
                            font = ResourcesCompat.getFont(
                                context,
                                www.sanju.motiontoast.R.font.helvetica_regular
                            ),
                            style = MotionToastStyle.SUCCESS,
                            message = "Photo saved to gallery",
                            title = "Hello"
                        )
                    }
                }
            }, Handler(Looper.getMainLooper()))
        }
    }
}