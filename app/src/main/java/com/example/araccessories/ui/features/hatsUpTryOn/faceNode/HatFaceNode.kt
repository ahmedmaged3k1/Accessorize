package com.example.araccessories.ui.features.hatsUpTryOn.faceNode


import android.content.Context
import android.net.Uri
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode

class HatFaceNode(augmentedFace: AugmentedFace?,
                  val context: Context,
                  val localScale: Scale?,
                  var  localPosition: Position?,
                  val model:String?
): AugmentedFaceNode(augmentedFace) {

    private var headNode: Node? = null
    companion object {
        enum class FaceRegion {
            Head
        }
    }

    override fun onActivate() {
        super.onActivate()
        headNode = Node()
        headNode?.setParent(this)



        ViewRenderable.builder()
            .setView(context, R.layout.element_layout)
            .setSource(context, Uri.parse(model))
            .build()
            .thenAccept { uiRenderable: ViewRenderable ->
                uiRenderable.isShadowCaster = false
                uiRenderable.isShadowReceiver = false
                headNode?.renderable = uiRenderable
                //uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.hat)
            }
            .exceptionally { throwable: Throwable? ->
                throw AssertionError(
                    "Could not create ui element",
                    throwable
                )
            }

    }

    private fun getRegionPose(region: FaceRegion) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {

            return when (region) {
                FaceRegion.Head ->
                    Vector3(buffer.get(10 * 3),
                        buffer.get(10 * 3 + 1),
                        buffer.get(10 * 3 + 2))


            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace?.let {
            getRegionPose(FaceRegion.Head)?.let {
                headNode?.localScale = Vector3(localScale!!.x, localScale.y, localScale.z)
                headNode?.localPosition = Vector3(localPosition!!.x, localPosition!!.y, localPosition!!.z)

            }

        }
    }
}