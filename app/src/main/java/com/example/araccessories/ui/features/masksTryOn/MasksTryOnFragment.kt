package com.example.araccessories.ui.features.masksTryOn

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.HelperFunctions
import com.example.araccessories.ui.features.masksTryOn.faceNode.MaskNode
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class MasksTryOnFragment : Fragment() {
    var faceNodeMap = HashMap<AugmentedFace, MaskNode>()
    private val args by navArgs<MasksTryOnFragmentArgs>()
    lateinit var arFragment: ArFragment
    private lateinit var captureShot: ImageButton
    private val viewModel: MasksTryOnViewModel by viewModels()
    private lateinit var productModelRenderable : ModelRenderable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_masks_try_on, container, false)
        if (!HelperFunctions.checkIsSupportedDeviceOrFinish(context,activity)) {
            view?.findNavController()
                ?.navigate(R.id.action_masksTryOnFragment_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_masks) as? ArFragment
            ?: return view
        var progressBar : ProgressBar = view.findViewById(R.id.maskProgressBar)
        viewModel.tryOnProduct(null, arFragment, requireActivity().applicationContext, args.products.modelSize, args.products.modelPosition, args.products.modelLink,progressBar)
        captureShot = view.findViewById(R.id.captureImageMasks)
        takeSnapShot()
        return view
    }

    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext())
        }
    }
    private fun initializeModel (){
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse(args.products.modelLink))
            .build()
            .thenAccept { modelRenderable ->
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
                productModelRenderable=modelRenderable

            }
    }


}