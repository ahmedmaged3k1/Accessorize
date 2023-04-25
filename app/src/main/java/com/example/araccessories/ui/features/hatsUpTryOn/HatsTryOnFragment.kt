package com.example.araccessories.ui.features.hatsUpTryOn

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
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.AugmentedFace
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
        if (!HelperFunctions.checkIsSupportedDeviceOrFinish(context,activity)) {
            view?.findNavController()?.navigate(R.id.action_hatsTryOnFragment_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_hats) as? ArFragment ?: return view
        viewModel.tryOnProduct(args.product.productModel, arFragment,requireContext(), args.product.localScale,args.product.localPosition,args.product.productId)
        captureShot = view.findViewById(R.id.captureImageHats)
        takeSnapShot()

        return  view
    }

    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext())
        }
    }



}