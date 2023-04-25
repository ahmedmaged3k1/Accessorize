package com.example.araccessories.ui.features.makeUpTryOn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.HelperFunctions
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode

class MakeUpTryOnFragment : Fragment() {
    lateinit var arFragment: ArFragment
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private val args by navArgs<MakeUpTryOnFragmentArgs>()
    private val viewModel: MakeUpTryOnViewModel by viewModels()
    private lateinit var captureShot: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_make_up_try_on, container, false)
        if (!HelperFunctions.checkIsSupportedDeviceOrFinish(context,activity)) {
            view?.findNavController()?.navigate(R.id.action_makeUpTryOnFragment_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_makeUp) as? ArFragment
            ?: return view
        captureShot = view.findViewById(R.id.captureImageMakeUp)
        viewModel.tryOnProduct(args.product.productModel, arFragment,requireContext(),args.product.productImage[0])
        takeSnapShot()
        return view
    }
    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext())
        }
    }
}