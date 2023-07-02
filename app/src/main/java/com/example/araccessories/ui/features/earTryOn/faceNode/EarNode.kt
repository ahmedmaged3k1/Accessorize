package com.example.araccessories.ui.features.earTryOn.faceNode

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

class EarNode (augmentedFace: AugmentedFace?,
               val context: Context,
               val localScale: Scale?,
               var  localPosition: Position?,
               val model:String?) : AugmentedFaceNode(augmentedFace) {

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
                //Uri.parse(model)
            .setSource(context, Uri.parse(model))
            .build()
            .thenAccept { modelRenderable ->

                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
                earNode?.renderable=modelRenderable
            }
    }
    private fun getRegionPose(region: FaceRegions) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {

            return when (region) {
                FaceRegions.EARS ->
                    Vector3(buffer.get(227 * 3),
                        buffer.get(116 * 3 + 1),
                        buffer.get(123 * 3 + 2))


            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace.let {face ->
            getRegionPose(FaceRegions.EARS).let {
                if (it != null) {
                    earNode?.localPosition = Vector3(it.x+(localPosition!!.x), it.y+(localPosition!!.y) , it.z+(localPosition!!.z) )
                   // earNode?.localPosition = Vector3(it.x, it.y-0.07f, it.z -0.08f)
                }

                earNode?.localScale = Vector3(localScale!!.x, localScale!!.y,localScale!!.z)
               // earNode?.localScale = Vector3(0.12f, 0.12f, 0.12f)

            }

        }

    }
}
// tall diamond
// position = 0 , -0.72 ,  -0.08
// scale = 0.12 , 0.12 ,0.12

//--------------------------
//tall_4 diamond_earrings
// position = 0,-0.072 ,-0.08
// scale = 0.12 , 0.12 ,0.12
//--------------------------
//triangle_earrings
//if (it != null) {
//    earNode?.localPosition = Vector3(it.x, it.y-0.06f, it.z -0.08f)
//}
//earNode?.localScale = Vector3(0.12f, 0.12f, 0.12f)
//}


//-------------------------------
// wedn 1
//if (it != null) {
//    earNode?.localPosition = Vector3(it.x, it.y-0.08f, it.z -0.08f)
//}
////  earNode?.localScale = Vector3(0.12f, 0.05f, 0.05f)
//}
//ear
//if (it != null) {
//    //earNode?.localPosition = Vector3(it.x, it.y-0.04f , it.z-0.04f )
//}
//earNode?.localScale = Vector3(8f, 4f, 4f)
//}