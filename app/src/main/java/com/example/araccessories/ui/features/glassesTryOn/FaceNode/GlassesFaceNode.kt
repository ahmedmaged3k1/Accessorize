package com.example.araccessories.ui.features.glassesTryOn.FaceNode

import android.net.Uri
import com.example.araccessories.R

import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3

import com.google.ar.sceneform.ux.AugmentedFaceNode

class GlassesFaceNode(augmentedFace: AugmentedFace?) : AugmentedFaceNode(augmentedFace){
    private var eyeNodeLeft: Node? = null
    private var eyeNodeRight: Node? = null



    companion object {
        enum class FaceRegion {
            LEFT_EYE,
            RIGHT_EYE

        }
    }
    override fun onActivate() {
        super.onActivate()
        eyeNodeLeft = Node()
        eyeNodeLeft?.setParent(this)
        eyeNodeRight = Node()
        eyeNodeRight?.setParent(this)
    }
    private fun getRegionPose(region: FaceRegion) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {

            return when (region) {
                FaceRegion.LEFT_EYE ->
                    Vector3(buffer.get(374 * 3),buffer.get(374 * 3 + 1),  buffer.get(374 * 3 + 2))
                FaceRegion.RIGHT_EYE ->
                    Vector3(buffer.get(145 * 3),buffer.get(145 * 3 + 1),  buffer.get(145 * 3 + 2))

            }
        }
        return null
    }
    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace?.let {face ->
            getRegionPose(FaceRegion.LEFT_EYE)?.let {
                eyeNodeLeft?.localPosition = Vector3(it.x, it.y - 0.035f, it.z + 0.015f)
                eyeNodeLeft?.localScale = Vector3(0.055f, 0.055f, 0.055f)
                eyeNodeLeft?.localRotation = Quaternion.axisAngle(Vector3(0.0f, 0.0f, 1.0f), -10f)
            }
            getRegionPose(FaceRegion.RIGHT_EYE)?.let {
                eyeNodeRight?.localPosition = Vector3(it.x, it.y - 0.035f, it.z + 0.015f)
                eyeNodeRight?.localScale = Vector3(0.055f, 0.055f, 0.055f)
                eyeNodeRight?.localRotation = Quaternion.axisAngle(Vector3(0.0f, 0.0f, 1.0f), 10f)
            }
        }
    }


}