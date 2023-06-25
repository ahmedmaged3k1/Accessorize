package com.example.araccessories.ui.features.glassesTryOn

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect

import android.media.MediaRecorder
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.ui.features.glassesTryOn.FaceNode.GlassesFaceNode
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GlassesTryOnViewModel :ViewModel() {
    private var isDepthSupported = false
    private lateinit var sceneView: ArSceneView
    private lateinit var scene: Scene
    private lateinit var config: Config

    private var faceRegionsRenderables: MutableList<ModelRenderable?> = mutableListOf()
    private var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()

    private fun configureArSession(productModels: List<ModelRenderable?>, arFragment: ArFragment) {
        sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        scene = sceneView.scene
        faceRegionsRenderables = productModels.toMutableList()
    }

    private fun enableDepth() {
        config = sceneView.session!!.config
        isDepthSupported = sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
        if (isDepthSupported) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC)
        } else {
            config.setDepthMode(Config.DepthMode.DISABLED)
        }
        sceneView.session!!.configure(config)
    }

    fun tryOnProduct(productModels: List<ModelRenderable?>, arFragment: ArFragment) {
        configureArSession(productModels, arFragment)
        scene.addOnUpdateListener {
            if (faceRegionsRenderables.isNotEmpty()) {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)
                    ?.let { augmentedFaces ->
                        enableDepth()
                        for (face in augmentedFaces) {
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
        val modelIndex = faceNodeMap.size % faceRegionsRenderables.size
        val faceNode = AugmentedFaceNode(face)
        faceNode.setParent(scene)
        faceNode.faceRegionsRenderable = faceRegionsRenderables[modelIndex]
        faceNodeMap[face] = faceNode
    }

    private fun removeRedundantModels() {
        val iterator = faceNodeMap.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val face = entry.key
            if (face.trackingState == TrackingState.STOPPED) {
                val faceNode = entry.value
                faceNode.setParent(null)
                iterator.remove()
            }
        }
    }

    fun takeSnapShot(context : Context,activity: Activity){
        viewModelScope.launch {
            val width =  sceneView.width
            val height =  sceneView.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            PixelCopy.request(sceneView,bitmap,{ res ->
                val filename = "Try On_${System.currentTimeMillis()}.png"
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                }
                val contentResolver = context.contentResolver
                val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
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

    private fun createVideoFile(context: Context): File {
        // Create a unique filename
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val videoFileName = "VIDEO_$timeStamp.mp4"

        // Get the directory to save the video
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return File(storageDir, videoFileName)
    }





}