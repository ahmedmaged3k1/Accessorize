package com.example.araccessories.ui.features.hatsUpTryOn

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.ui.core.utilities.GlassesActivity
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentHatsTryOnBinding
import com.example.araccessories.ui.features.glassesTryOn.GlassesTryOnViewModel
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment


class HatsTryOnFragment : Fragment() {

    lateinit var arFragment: ArFragment
    private val args by navArgs<HatsTryOnFragmentArgs>()
    var faceNodeMap = HashMap<AugmentedFace, HatFaceNode>()
    private lateinit var captureShot: ImageButton
    private val viewModel: HatsTryOnViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_hats_try_on, container, false)
        if (!checkIsSupportedDeviceOrFinish()) {
            view?.findNavController()?.navigate(R.id.action_hatsTryOnFragment_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_hats) as? ArFragment ?: return view
        viewModel.tryOnProduct(args.product.productModel, arFragment,requireContext(), args.product.localScale,args.product.localPosition,args.product.productId)
        captureShot = view.findViewById(R.id.captureImageHats)
        takeSnapShot()

        return  view
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
                    "Sceneform requires OpenGL ES 3.0 or later",
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