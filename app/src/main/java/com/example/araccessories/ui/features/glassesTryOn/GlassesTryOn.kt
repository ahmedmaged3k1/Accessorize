package com.example.araccessories.ui.features.glassesTryOn

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.HelperFunctions
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GlassesTryOn : Fragment() {
    private val args by navArgs<GlassesTryOnArgs>()
    private lateinit var arFragment: ArFragment
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private val viewModel: GlassesTryOnViewModel by viewModels()
    private val RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO
    private val REQUEST_PERMISSION_CODE = 200

    private lateinit var captureShot: ImageButton
    private lateinit var record: Button

    private var isRecording = false
    private lateinit var mediaRecorder: MediaRecorder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_glasses_try_on, container, false)
        if (!HelperFunctions.checkIsSupportedDeviceOrFinish(context, activity)) {
            view?.findNavController()?.navigate(R.id.action_glassesTryOn_to_productDetailsFragment)
        }
        arFragment =
            childFragmentManager.findFragmentById(R.id.face_fragment_glasses) as? ArFragment
                ?: return view
        captureShot = view.findViewById(R.id.captureImageGlasses)

        initializeModel()
        takeSnapShot()

        // Check and request necessary permissions
        val permissions = arrayOf(RECORD_AUDIO_PERMISSION)
        val permissionCheck =
            ContextCompat.checkSelfPermission(requireContext(), RECORD_AUDIO_PERMISSION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_PERMISSION_CODE
            )
        }

        return view
    }

    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext(),requireActivity())
        }
    }

    private fun initializeModel() {
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse(args.products.modelLink))
            .build()
            .thenAccept { modelRenderable ->
                modelRenderable?.isShadowCaster = false
                modelRenderable?.isShadowReceiver = false
                val productModelRenderable: ModelRenderable? = modelRenderable
                viewModel.tryOnProduct(listOf(productModelRenderable), arFragment)
            }
            .exceptionally {
                Log.e(TAG, "Error loading model: ${it.localizedMessage}")
                return@exceptionally null
            }
    }



}
