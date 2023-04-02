package com.example.araccessories.ui.features.masksTryOn

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.example.araccessories.R
import com.example.araccessories.ui.features.masksTryOn.faceNode.MaskNode
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment

class MasksTryOnFragment : Fragment() {
    var faceNodeMap = HashMap<AugmentedFace, MaskNode>()
    private var faceMeshTexture: Texture? = null
    private var isDepthSupported = false
    private val args by navArgs<MasksTryOnFragmentArgs>()
    lateinit var arFragment: ArFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_masks_try_on, container, false)
        if (!checkIsSupportedDeviceOrFinish()) {
            //navigate

        }

        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_masks) as? ArFragment
            ?: return view
        initializeScene()
        return view
    }

    private fun initializeScene() {
        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene
        scene.addOnUpdateListener{
            sceneView.session
                ?.getAllTrackables(AugmentedFace::class.java)?.let {
                    val config: Config = sceneView.session!!.config
                    config.augmentedFaceMode= Config.AugmentedFaceMode.MESH3D
                    isDepthSupported = sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                    if (isDepthSupported) {
                        config.setDepthMode(Config.DepthMode.AUTOMATIC)
                    } else {
                        config.setDepthMode(Config.DepthMode.DISABLED)
                    }
                    sceneView.session!!.configure(config)
                    for (augmentedFace in it) {
                        if (!faceNodeMap.containsKey(augmentedFace)) {
                            val faceNode = MaskNode(augmentedFace,this.requireActivity(),args.product.productId,args.product.localScale,args.product.localPosition)

                            faceNode.setParent(scene)

                            faceNodeMap[augmentedFace] = faceNode


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