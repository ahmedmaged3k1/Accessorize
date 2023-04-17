package com.example.araccessories.ui.features.glassesTryOn

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentGlassesTryOnBinding
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Node.OnTouchListener
import com.google.ar.sceneform.math.Quaternion

import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.*
import com.google.ar.schemas.lull.Vec2
import kotlinx.android.synthetic.main.activity_glasses.*
import kotlinx.android.synthetic.main.fragment_glasses_try_on.*
import java.util.*
import kotlin.collections.HashMap


class GlassesTryOn :Fragment()   {
    private lateinit var binding: FragmentGlassesTryOnBinding
    private var previousX = 0f
    private var previousY = 0f

    private var isDepthSupported = false
    private val args by navArgs<GlassesTryOnArgs>()

    lateinit var arFragment: ArFragment

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

        }


        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_glasses) as? ArFragment ?: return view



    faceRegionsRenderable=args.product.productModel

        initializeScene()
        //moveModel()
        return view
    }

    private fun moveModel(){
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
    }
    private fun initializeScene() {
        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene
        scene.addOnUpdateListener {
            if (faceRegionsRenderable != null) {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        val config: Config = sceneView.session!!.getConfig()
                        isDepthSupported =
                            sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                        if (isDepthSupported) {
                            config.setDepthMode(Config.DepthMode.AUTOMATIC)
                        } else {
                            config.setDepthMode(Config.DepthMode.DISABLED)
                        }
                        sceneView.session!!.configure(config)
                        for (face in it) {
                            if (!faceNodeMap.containsKey(face)) {
                                val faceNode = AugmentedFaceNode(face)
                                faceNode.setParent(scene)
                                face.getRegionPose(AugmentedFace.RegionType.NOSE_TIP)

                                // Set the 3D model as a child of the face node
                                val modelNode = Node()
                                modelNode.setParent(faceNode)
                                modelNode.renderable = faceRegionsRenderable

                                // Add a gesture listener to the model node
                                modelNode.setOnTapListener { _, _ ->
                                    // Handle tap on the model
                                    // For example, display information about the model
                                }
                                modelNode.setOnTouchListener(object : Node.OnTouchListener {
                                    var previousX = 0f
                                    var previousY = 0f

                                    override fun onTouch(
                                        hitTestResult: HitTestResult?,
                                        event: MotionEvent?
                                    ): Boolean {
                                        when (event?.action) {
                                            MotionEvent.ACTION_DOWN -> {
                                                previousX = event.x
                                                previousY = event.y
                                            }
                                            MotionEvent.ACTION_MOVE -> {
                                                val dx = event.x - previousX
                                                val dy = event.y - previousY
                                                previousX = event.x
                                                previousY = event.y
                                                // Translate the model
                                                modelNode.localPosition =
                                                    Vector3(
                                                        modelNode.localPosition.x + dx / 200f,
                                                        modelNode.localPosition.y - dy / 200f,
                                                        modelNode.localPosition.z
                                                    )
                                                // Rotate the model
                                                modelNode.localRotation =
                                                    Quaternion.axisAngle(Vector3(0f, 1f, 0f), dx)
                                            }
                                        }
                                        return true
                                    }
                                })

                                faceNodeMap[face] = faceNode
                            }
                        }

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
            }
        }
    }



    /*private fun initializeScene() {

        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene
        scene.addOnUpdateListener {
            if (faceRegionsRenderable != null) {
                sceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        val config: Config = sceneView.session!!.getConfig()
                        isDepthSupported =
                            sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                        if (isDepthSupported) {
                            config.setDepthMode(Config.DepthMode.AUTOMATIC)
                        } else {
                            config.setDepthMode(Config.DepthMode.DISABLED)
                        }
                        sceneView.session!!.configure(config)
                        for (face in it) {
                            if (!faceNodeMap.containsKey(face)) {
                                val faceNode = AugmentedFaceNode(face)
                                faceNode.setParent(scene)
                                face.getRegionPose(AugmentedFace.RegionType.NOSE_TIP)
                               //faceRegionsRenderable=args.product.productModel

                                faceNode.faceRegionsRenderable = faceRegionsRenderable
                                faceNodeMap[face] = faceNode
                            }
                        }

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
            }
        }
    }*/

    private fun faceDetectionAndTryOn() {

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




}