package com.example.araccessories.ui.core

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.TransformableNode

class ModelTouchListener(
    private val arSceneView: Scene,
    private val arSession: Session,
    private val activity: AppCompatActivity,
    private val selectedModelRenderable: ModelRenderable
) : Scene.OnTouchListener {

    private var previousX = 0f
    private var previousY = 0f
    private var selectedNode: TransformableNode? = null

    override fun onSceneTouch(hitTestResult: HitTestResult?, event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }

        val displayMetrics = activity.resources.displayMetrics
        val viewportWidth = displayMetrics.widthPixels
        val viewportHeight = displayMetrics.heightPixels
        val screenPoint = Vector3(
            event.x / viewportWidth * 2 - 1,
            -(event.y / viewportHeight * 2 - 1),
            0.0f
        )

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Find the model node that was tapped
              //  val hitResults = arSceneView.hitTest(screenPoint)
               /* for (hitResult in hitResults) {
                  val trackable = hitResult.trackable
                    if (trackable is Plane && trackable.isPoseInPolygon(hitResult.hitPose)) {
                        // Check if the tapped node is a model node
                        if (selectedNode != null) {
                            selectedNode!!.setParent(null)
                            selectedNode = null
                        }
*/
                    /*    val anchorNode = AnchorNode(hitResult.createAnchor())
                     //   val model = TransformableNode(arSceneView.transformationSystem)
                        model.setParent(anchorNode)
                        model.renderable = selectedModelRenderable // set your model renderable
                        model.select()
                        selectedNode = model
                        //arSceneView.scene.addChild(anchorNode)
                        return true
                    }
                }
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                // Move the model based on the touch movement
                if (selectedNode != null) {
                    val deltaX = event.x - previousX
                    val deltaY = event.y - previousY
                    selectedNode!!.localPosition = Vector3(
                        selectedNode!!.localPosition.x + deltaX / 100f,
                        selectedNode!!.localPosition.y - deltaY / 100f,
                        selectedNode!!.localPosition.z
                    )
                }*/
                previousX = event.x
                previousY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Deselect the model node
                if (selectedNode != null) {
                    selectedNode!!.setParent(null)
                    selectedNode = null
                    return true
                }
            }
        }
        return false
    }
}

/*private fun Scene.hitTest(screenPoint: Vector3): Any {
    // return Vector3(this.x, this.y, this.z)
}
*/
