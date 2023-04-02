package com.example.araccessories.ui.core.utilities

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Position
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Scale
import com.example.araccessories.ui.core.arSession.FaceArFragment
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import kotlinx.android.synthetic.main.activity_regions.*

class FaceLandmarksActivity : AppCompatActivity() {
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }

    private var isDepthSupported = false

    lateinit var arFragment: FaceArFragment
    var faceNodeMap = HashMap<AugmentedFace, HatFaceNode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDeviceOrFinish()) {
            return
        }

        setContentView(R.layout.activity_regions)
        arFragment = face_fragment as FaceArFragment

        button_refresh.visibility = View.GONE

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


                            val faceNode = HatFaceNode(f, this, Scale(0.09f, 0.07f, 0.09f),
                                Position(0.09f, 0.07f, 0.09f),"hat.sfb"
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
            if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show()
                finish()
                return false
            }
        }
        return true
    }
}