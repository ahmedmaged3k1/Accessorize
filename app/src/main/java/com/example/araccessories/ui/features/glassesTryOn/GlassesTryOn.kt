package com.example.araccessories.ui.features.glassesTryOn

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.google.ar.core.*
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.*
import kotlinx.android.synthetic.main.activity_glasses.*
import kotlinx.android.synthetic.main.fragment_glasses_try_on.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*


class GlassesTryOn :Fragment()   {
    private var isDepthSupported = false
    private val args by navArgs<GlassesTryOnArgs>()
    private lateinit var arFragment: ArFragment
    private lateinit var sceneView: ArSceneView
    private lateinit var scene: Scene
    private lateinit var config: Config
    private var faceRegionsRenderable: ModelRenderable? = null
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_glasses_try_on, container, false)
        if (!checkIsSupportedDeviceOrFinish()) {
            //navigate
            view?.findNavController()?.navigate(R.id.action_glassesTryOn_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_glasses) as? ArFragment ?: return view
        configureArSession()
        tryOnProduct()
        //moveModel()
        return view
    }
    private fun configureArSession(){
        sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        scene = sceneView.scene
        faceRegionsRenderable=args.product.productModel
    }
    private fun enableDepth(){
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
    private fun tryOnProduct() {
        scene.addOnUpdateListener {
            if (faceRegionsRenderable != null) {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        enableDepth()
                        for (face in it) {
                            if (!faceNodeMap.containsKey(face)) {
                             attachModel(face)
                                CoroutineScope(Dispatchers.IO).launch{
                                    takeSnapShot()
                                }
                            }
                        }
                  removeRedundantModels()
                    }
            }
        }
    }
    private fun attachModel(face : AugmentedFace){
        val faceNode = AugmentedFaceNode(face)
        faceNode.setParent(scene)
        face.getRegionPose(AugmentedFace.RegionType.NOSE_TIP)
        faceNode.faceRegionsRenderable = faceRegionsRenderable
        faceNodeMap[face] = faceNode
    }
    private fun removeRedundantModels(){
        // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
        val iter = faceNodeMap.entries.iterator()
        while (iter.hasNext()) {
            val entry = iter.next()
            val face = entry.key
            if (face.trackingState == TrackingState.STOPPED) {
                val faceNode = entry.value
                faceNode.setParent(null)
                iter.remove()
            }
        }
    }
    private fun takeSnapShot(){
        captureImage.setOnClickListener{
            val width =  sceneView.width
            val height =  sceneView.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            PixelCopy.request(sceneView,bitmap,{res ->
                val filename = "${args.product.productName} Try On_${System.currentTimeMillis()}.png"
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                }
                val contentResolver = requireContext().contentResolver
                val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let {
                    contentResolver.openOutputStream(it).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        Toast.makeText(requireContext(), "Photo saved to gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            }, Handler(Looper.getMainLooper()))


        }
    }


    private fun checkIsSupportedDeviceOrFinish(): Boolean {
        if (ArCoreApk.getInstance()
                .checkAvailability(this.context) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE
        ) {
            Toast.makeText(this.context, "Augmented Faces requires ARCore", Toast.LENGTH_LONG)
                .show()
            activity?.finish()
            return false
        }
        val openGlVersionString =
            (activity?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
                ?.deviceConfigurationInfo
                ?.glEsVersion

        openGlVersionString?.let { s ->
            if (java.lang.Double.parseDouble(openGlVersionString) < GlassesActivity.MIN_OPENGL_VERSION) {
                Toast.makeText(
                    this.context,
                    "Sceneform requires OpenGL ES 3.0 or later",
                    Toast.LENGTH_LONG
                )
                    .show()
                activity?.finish()
                return false
            }
        }
        return true
    }
    /*   private fun moveModel(){
           arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
               val anchor = hitResult.createAnchor()
               val anchorNode = AnchorNode(anchor)
               anchorNode.setParent(arFragment.arSceneView.scene)
               val node = TransformableNode(arFragment.transformationSystem)
               node.renderable = faceRegionsRenderable
               node.setParent(anchorNode)
               arFragment.arSceneView.scene.addChild(anchorNode)
               node.select()
           }
       }*/



}