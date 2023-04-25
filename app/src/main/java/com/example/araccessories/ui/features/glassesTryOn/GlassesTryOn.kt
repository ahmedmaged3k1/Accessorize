package com.example.araccessories.ui.features.glassesTryOn

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode


class GlassesTryOn : Fragment() {
    private val args by navArgs<GlassesTryOnArgs>()
    private lateinit var arFragment: ArFragment
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private val viewModel: GlassesTryOnViewModel by viewModels()
    private lateinit var captureShot: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_glasses_try_on, container, false)
        if (!checkIsSupportedDeviceOrFinish()) {
            view?.findNavController()?.navigate(R.id.action_glassesTryOn_to_productDetailsFragment)
        }
        arFragment =
            childFragmentManager.findFragmentById(R.id.face_fragment_glasses) as? ArFragment
                ?: return view
        captureShot = view.findViewById(R.id.captureImageGlasses)
        viewModel.tryOnProduct(args.product.productModel, arFragment)
        takeSnapShot()
        return view
    }


    private fun checkIsSupportedDeviceOrFinish(): Boolean {
        if (ArCoreApk.getInstance()
                .checkAvailability(this.context) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE
        ) {
            Toast.makeText(this.context, "Augmented Faces requires ARCore", Toast.LENGTH_LONG)
                .show()
            activity?.finish()
            return false
        }
        val openGlVersionString =
            (activity?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
                ?.deviceConfigurationInfo
                ?.glEsVersion

        openGlVersionString?.let {
            if (java.lang.Double.parseDouble(openGlVersionString) < GlassesActivity.MIN_OPENGL_VERSION) {
                Toast.makeText(
                    this.context,
                    "Scene-form requires OpenGL ES 3.0 or later",
                    Toast.LENGTH_LONG
                )
                    .show()
                activity?.finish()
                return false
            }
        }
        return true
    }

    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext())
        }


    }


}