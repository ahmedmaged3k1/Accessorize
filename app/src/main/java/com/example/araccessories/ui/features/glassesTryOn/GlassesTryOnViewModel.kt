package com.example.araccessories.ui.features.glassesTryOn

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashMap

class GlassesTryOnViewModel :ViewModel() {
    private var isDepthSupported = false

    private lateinit var sceneView: ArSceneView
    private lateinit var scene: Scene
    private lateinit var config: Config
    private var faceRegionsRenderable: ModelRenderable? = null
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private fun configureArSession(productModel : ModelRenderable ,arFragment: ArFragment  ){
        sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        scene = sceneView.scene
        faceRegionsRenderable=productModel
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
                                    //takeSnapShot()
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


}