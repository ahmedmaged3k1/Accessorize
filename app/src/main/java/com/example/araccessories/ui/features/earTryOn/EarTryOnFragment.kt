package com.example.araccessories.ui.features.earTryOn

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.ui.core.HelperFunctions
import com.example.araccessories.ui.features.earTryOn.faceNode.EarNode
import com.example.araccessories.ui.features.glassesTryOn.GlassesTryOnArgs
import com.example.araccessories.ui.features.glassesTryOn.GlassesTryOnViewModel
import com.example.araccessories.ui.features.hatsUpTryOn.HatsTryOnFragmentArgs
import com.example.araccessories.ui.features.hatsUpTryOn.HatsTryOnViewModel
import com.example.araccessories.ui.features.hatsUpTryOn.faceNode.HatFaceNode
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode


class EarTryOnFragment : Fragment() {
    lateinit var arFragment: ArFragment
    private val args by navArgs<EarTryOnFragmentArgs>()
    var faceNodeMap = HashMap<AugmentedFace, EarNode>()
    private lateinit var captureShot: ImageButton
    private val viewModel: EarTryOnViewModel by viewModels()
    private lateinit var productModelRenderable :  ModelRenderable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_ear_try_on, container, false)
        if (!HelperFunctions.checkIsSupportedDeviceOrFinish(context,activity)) {
            view?.findNavController()?.navigate(R.id.action_earTryOnFragment_to_productDetailsFragment)
        }
        arFragment = childFragmentManager.findFragmentById(R.id.face_fragment_ear) as? ArFragment ?: return view
        initializeModel()
        viewModel.tryOnProduct(null, arFragment,requireActivity().applicationContext, args.products.modelSize,args.products.modelPosition,args.products.modelLink)
        captureShot = view.findViewById(R.id.captureImageEar)
        takeSnapShot()

        return  view
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