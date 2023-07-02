package com.example.araccessories.ui.features.makeUpTryOn

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
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
        Log.d(TAG, "initializeModel 1 : ${args.products.toString()}")
        viewModel.tryOnProduct(null, arFragment,requireActivity().applicationContext,args.products.modelLink)

        //initializeModel()
        takeSnapShot()
        return view
    }
    private fun takeSnapShot() {
        captureShot.setOnClickListener {
            viewModel.takeSnapShot(requireContext(),requireActivity())
        }
    }
    private fun initializeModel (){
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse(args.products.modelLink))
            .build()
            .thenAccept { modelRenderable ->
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
                val productModelRenderable =modelRenderable
                Log.d(TAG, "initializeModel: ${args.products.toString()}")

            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

}