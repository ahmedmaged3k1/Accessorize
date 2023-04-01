package com.example.araccessories

import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.araccessories.ui.core.FaceArFragment
import com.example.araccessories.ui.core.customfacenodes.MaskNode
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.Config.AugmentedFaceMode
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import kotlinx.android.synthetic.main.activity_regions.*

class FaceMaskActivity : AppCompatActivity() {
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }
    lateinit var arFragment: FaceArFragment
    var faceNodeMap = HashMap<AugmentedFace, MaskNode>()
    private var faceMeshTexture: Texture? = null
    private var isDepthSupported = false
    private lateinit var modelRenderable  : ModelRenderable
    private var maskNode: Node? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_mask)
        if (!checkIsSupportedDeviceOrFinish()) {
            return
        }
        arFragment = face_fragment as FaceArFragment



//        ModelRenderable.builder()
//            .setSource(this, Uri.parse("cat.sfb"))
//            .build()
//            .thenAccept { renderable ->
//                renderable.isShadowCaster = false
//                renderable.isShadowReceiver = false
//                modelRenderable=renderable
//
//            }
//        Texture.builder()
//            .setSource(this, R.drawable.fox_face_mesh_texture)
//            .build()
//            .thenAccept { texture -> faceMeshTexture = texture }
        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene
//        val config = Config(sceneView.session)
//        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
//        sceneView.session?.configure(config)
        scene.addOnUpdateListener{
            sceneView.session
                ?.getAllTrackables(AugmentedFace::class.java)?.let {
                    val config: Config = sceneView.session!!.config
                    config.augmentedFaceMode= AugmentedFaceMode.MESH3D
                    isDepthSupported = sceneView.session!!.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                    if (isDepthSupported) {
                        config.setDepthMode(Config.DepthMode.AUTOMATIC)
                    } else {
                        config.setDepthMode(Config.DepthMode.DISABLED)
                    }
                    sceneView.session!!.configure(config)
                    for (augmentedFace in it) {
                        if (!faceNodeMap.containsKey(augmentedFace)) {
                            val faceNode = MaskNode(augmentedFace,this)

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
//    private fun checkDepthSupportedOrNot() : Boolean{
//
//    }
    private fun checkIsSupportedDeviceOrFinish() : Boolean {
        if (ArCoreApk.getInstance().checkAvailability(this) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Toast.makeText(this, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show()
            finish()
            return false
        }
        val openGlVersionString =  (getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
            ?.deviceConfigurationInfo
            ?.glEsVersion

        openGlVersionString?.let { s ->
            if (java.lang.Double.parseDouble(openGlVersionString) < FaceRegionsActivity.MIN_OPENGL_VERSION) {
                Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show()
                finish()
                return false
            }
        }
        return true

    }
    }

