package com.example.araccessories

import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.widget.Toast
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.Config.AugmentedFaceMode
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.android.synthetic.main.activity_regions.*

class FaceMaskActivity : AppCompatActivity() {
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }
    lateinit var arFragment: FaceArFragment
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var faceMeshTexture: Texture? = null
    private var isDepthSupported = false
    private lateinit var modelRenderable  : ModelRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_mask)
        if (!checkIsSupportedDeviceOrFinish()) {
            return
        }
        arFragment = face_fragment as FaceArFragment



        ModelRenderable.builder()
            .setSource(this, Uri.parse("cat.sfb"))
            .build()
            .thenAccept { renderable ->
                renderable.isShadowCaster = false
                renderable.isShadowReceiver = false
                modelRenderable=renderable

            }
        Texture.builder()
            .setSource(this, R.drawable.fox_face_mesh_texture)
            .build()
            .thenAccept { texture -> faceMeshTexture = texture }
        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene
//        val config = Config(sceneView.session)
//        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
//        sceneView.session?.configure(config)
        scene.addOnUpdateListener{
            sceneView.session
                ?.getAllTrackables(AugmentedFace::class.java)?.let {
                    val config: Config = sceneView.session!!.getConfig()
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
                            val faceNode = AugmentedFaceNode(augmentedFace)
//                            val buffer =  augmentedFace.meshVertices
//                            var vector =Vector3(buffer.get(1 * 3),buffer.get(1 * 3 + 1),  buffer.get(1 * 3 + 2))
//                            faceNode.localPosition = Vector3(vector.x +0f, vector.y -1.6f, vector.z +0)
                           faceNode.localScale= Vector3(0.4f, 0.1f, 0.2f)

                            //augmentedFace.getRegionPose(AugmentedFace.RegionType.NOSE_TIP)

                            faceNode.setParent(scene)
                            faceNode.faceRegionsRenderable=modelRenderable
                            //faceNode.faceMeshTexture=faceMeshTexture

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

