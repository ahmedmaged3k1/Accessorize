package com.example.araccessories.ui.features.hatsUpTryOn

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.araccessories.GlassesActivity
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Position
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Scale
import com.example.araccessories.databinding.FragmentHatsTryOnBinding
import com.example.araccessories.ui.core.arSession.FaceArFragment
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment


class HatsTryOnFragment : Fragment() {
    private lateinit var binding: FragmentHatsTryOnBinding
    lateinit var arFragment: ArFragment
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }

    private var isDepthSupported = false
    var faceNodeMap = HashMap<AugmentedFace, HatFaceNode>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (!checkIsSupportedDeviceOrFinish()) {
            //navigate

        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_hats) as? ArFragment ?: return view


        return inflater.inflate(R.layout.fragment_hats_try_on, container, false)
    }
    private fun initializeScene(){

        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene


        scene.addOnUpdateListener {
            sceneView.session
                ?.getAllTrackables(AugmentedFace::class.java)?.let {
                    val config: Config = sceneView.session!!.config
                    isDepthSupported = sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                    if (isDepthSupported) {
                        config.setDepthMode(Config.DepthMode.AUTOMATIC)
                    } else {
                        config.setDepthMode(Config.DepthMode.DISABLED)
                    }
                    sceneView.session!!.configure(config)
                    for (f in it) {
                        if (!faceNodeMap.containsKey(f)) {
                            val faceNode = HatFaceNode(f, this.requireActivity(),
                                Scale(0.09f, 0.07f, 0.09f),
                                Position(0.09f, 0.07f, 0.09f)
                            )
                            faceNode.setParent(scene)
                            faceNodeMap[f] = faceNode
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