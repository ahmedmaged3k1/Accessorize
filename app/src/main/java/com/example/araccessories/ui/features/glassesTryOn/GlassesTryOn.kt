package com.example.araccessories.ui.features.glassesTryOn

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.araccessories.GlassesActivity
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentGlassesTryOnBinding
import com.example.araccessories.ui.core.FaceArFragment
import com.google.ar.core.*
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.android.synthetic.main.activity_glasses.*
import kotlinx.android.synthetic.main.fragment_glasses_try_on.*
import java.util.*
import kotlin.collections.HashMap


class GlassesTryOn :Fragment() {
    private lateinit var binding: FragmentGlassesTryOnBinding

    private var isDepthSupported = false
    private val args by navArgs<GlassesTryOnArgs>()

    lateinit var arFragment: ArFragment

    private var faceRegionsRenderable: ModelRenderable? = null
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_glasses_try_on, container, false)



        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_glasses) as? ArFragment ?: return view


        if (!checkIsSupportedDeviceOrFinish()) {
            //navigate

        }

        initializeScene()

        return view
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

                                faceNode.faceRegionsRenderable = args.product.productModel
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