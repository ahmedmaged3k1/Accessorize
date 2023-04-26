package com.example.araccessories.ui.features.masksTryOn.faceNode

import android.content.Context
import android.net.Uri
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode


class MaskNode(augmentedFace: AugmentedFace, val context:Context, val model:String,
               var localScale: Scale?,
               var  localPosition: Position?
) : AugmentedFaceNode(augmentedFace) {

    private var faceNode  : Node?  = null
    var x = 0f
    var y = 0f
    var z = 0f

    companion object{
        enum class FaceRegions{
            CENTER_FACE
        }
    }

    override fun onActivate() {
        super.onActivate()
        faceNode = Node()
        faceNode?.setParent(this)
        ModelRenderable.builder()
            .setSource(context, Uri.parse(model))
            .build()
            .thenAccept { modelRenderable ->

                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
                faceNode?.renderable=modelRenderable
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
               FaceRegions.CENTER_FACE ->
                   Vector3(buffer.get(152 * 3),
                       buffer.get(152 * 3 + 1),
                       buffer.get(152 * 3 + 2))


            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace.let {face ->
            getRegionPose(FaceRegions.CENTER_FACE).let {
                if (it != null) {
                    faceNode?.localPosition = Vector3(it.x+(localPosition!!.x), it.y+(localPosition!!.y) , it.z+(localPosition!!.z) )
                }
               faceNode?.localScale = Vector3(localScale!!.x, localScale!!.y,localScale!!.z)
            }

        }

    }
}

// Cat  Mask
//if (it != null) {
//    faceNode?.localPosition = Vector3(it.x, it.y-0.005f , it.z+0.017f )
//}
//faceNode?.localScale = Vector3(0.35f, 0.35f, 0.35f)