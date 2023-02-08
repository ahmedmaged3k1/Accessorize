package com.example.araccessories.ui.core.customfacenodes

import android.content.Context
import android.net.Uri
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode

class EarNode (augmentedFace: AugmentedFace, val context: Context) : AugmentedFaceNode(augmentedFace) {

    private var earNode  : Node?  = null
    var x = 0f
    var y = 0f
    var z = 0f

    companion object{
        enum class FaceRegions{
            EARS
        }
    }

    override fun onActivate() {
        super.onActivate()
        earNode = Node()
        earNode?.setParent(this)
        ModelRenderable.builder()
            .setSource(context, Uri.parse("wedn1.sfb"))
            .build()
            .thenAccept { modelRenderable ->

                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
                earNode?.renderable=modelRenderable
            }
//        ViewRenderable.builder()
//            .setView(context, R.layout.element_layout)
//            .setSource(context, Uri.parse("glasses.sfb"))
//            .build()
//            .thenAccept { uiRenderable: ViewRenderable ->
//                uiRenderable.isShadowCaster = false
//                uiRenderable.isShadowReceiver = false
//                faceNode?.renderable = uiRenderable
//                //uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.hat)
//            }
//            .exceptionally { throwable: Throwable? ->
//                throw AssertionError(
//                    "Could not create ui element",
//                    throwable
//                )
//            }
    }
    private fun getRegionPose(region: FaceRegions) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {

            return when (region) {
                FaceRegions.EARS->
                    Vector3(buffer.get(447 * 3),
                        buffer.get(447 * 3 + 1),
                        buffer.get(447 * 3 + 2))


            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace.let {face ->
            getRegionPose(FaceRegions.EARS).let {
                if (it != null) {
                   //- earNode?.localPosition = Vector3(it.x, it.y-0.02f , it.z-0.15f )
                }
            //   earNode?.localScale = Vector3(0.12f, 0.05f, 0.05f)
            }

        }

    }
}

//ear
//if (it != null) {
//    //earNode?.localPosition = Vector3(it.x, it.y-0.04f , it.z-0.04f )
//}
//earNode?.localScale = Vector3(8f, 4f, 4f)
//}