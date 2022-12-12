package com.example.araccessories.ui.features.augmentedImage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.araccessories.R
import com.example.araccessories.databinding.ActivityAugmentedImageBinding
import com.example.araccessories.ui.features.augmentedImage.helpers.CameraPermissionHelper
import com.example.araccessories.ui.features.augmentedImage.helpers.FullScreenHelper
import com.example.araccessories.ui.features.augmentedImage.helpers.SnackBarHelper
import com.google.ar.core.*
import com.google.ar.core.exceptions.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

class AugmentedImageActivity : AppCompatActivity() {

    private val TAG = AugmentedImageActivity::class.java.simpleName
    private var installRequested: Boolean = false
    private lateinit var binding: ActivityAugmentedImageBinding
    private var session: Session? = null
    private var shouldConfigureSession = false
    private val messageSnackBarHelper = SnackBarHelper()
    internal lateinit var dataView: CompletableFuture<ViewRenderable>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAugmentedImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializeSceneView()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(
                this, "Camera permissions are needed to run this application", Toast.LENGTH_LONG
            )
                .show()
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        FullScreenHelper.setFullScreenOnWindowFocusChanged(this, hasFocus)
    }
    private fun initializeSceneView() {
        makeInfoView()
        binding.arSceneView.scene.addOnUpdateListener(this::onUpdateFrame)
    }
    private fun onUpdateFrame(frameTime: FrameTime) {
        frameTime.toString()
        val frame = binding.arSceneView.arFrame
        val updatedAugmentedImages = frame?.getUpdatedTrackables(AugmentedImage::class.java)

        if (updatedAugmentedImages != null) {
            for (augmentedImage in updatedAugmentedImages) {
                if (augmentedImage.trackingState == TrackingState.TRACKING) {
                    // Check camera image matches our reference image

                    if (augmentedImage.name == "qrcode") {
                        //createRenderable(augmentedImage)
                        Log.d(TAG, "onUpdateFrame: founddddddddd")
                        createRenderable(augmentedImage)

                    }

                }
            }
        }
    }
    private fun makeInfoView(){
        dataView = ViewRenderable.builder().setView(this, R.layout.layout_bg)            .setSource(this, Uri.parse("tshirt.sfb"))
            .setSource(this, Uri.parse("tshirt.sfb"))
            .build()

    }
    private fun createRenderable(augmentedImage: AugmentedImage) {
        var renderable : ViewRenderable?=null
        try {
            renderable = dataView.get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        val node = Node()
        try {

            val anchorNode = AnchorNode(binding.arSceneView.session?.createAnchor(augmentedImage.centerPose))
            binding.arSceneView.scene.removeChild(anchorNode)
            val pose = Pose.makeTranslation(0.0f, 0.0f, 0.12f)
            node.localPosition = Vector3(pose.tx(), pose.ty(), pose.tz())
            node.renderable = renderable
            node.setParent(anchorNode)
            node.localRotation = Quaternion(pose.qx(), 90f, -90f, pose.qw())
            binding.arSceneView.scene.addChild(anchorNode)
            Log.d(TAG, "createRenderable: created")
           // setNodeData(renderable!!,name)
            //sensorsMap[name] = renderable!!
            makeInfoView()

        } catch (e: Exception) {
            e.toString()
        }
    }
    override fun onResume() {
        super.onResume()
        if (session == null) {
            var exception: Exception? = null
            var message: String? = null
            try {
                when (ArCoreApk.getInstance().requestInstall(this, !installRequested)) {
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        installRequested = true
                        return
                    }
                    ArCoreApk.InstallStatus.INSTALLED -> {
                    }
                }

                // ARCore requires camera permissions to operate. If we did not yet obtain runtime
                // permission on Android M and above, now is a good time to ask the user for it.
                if (!CameraPermissionHelper.hasCameraPermission(this)) {
                    CameraPermissionHelper.requestCameraPermission(this)
                    return
                }

                session = Session(/* context = */this)
            } catch (e: UnavailableArcoreNotInstalledException) {
                message = "Please install ARCore"
                exception = e
            } catch (e: UnavailableUserDeclinedInstallationException) {
                message = "Please install ARCore"
                exception = e
            } catch (e: UnavailableApkTooOldException) {
                message = "Please update ARCore"
                exception = e
            } catch (e: UnavailableSdkTooOldException) {
                message = "Please update this app"
                exception = e
            } catch (e: Exception) {
                message = "This device does not support AR"
                exception = e
            }

            if (message != null) {
                messageSnackBarHelper.showError(this, message)
                Log.e(TAG, "Exception creating session", exception)
                return
            }

            shouldConfigureSession = true
        }

        if (shouldConfigureSession) {
            configureSession()
            shouldConfigureSession = false
            binding.arSceneView.setupSession(session)
        }

        // Note that order matters - see the note in onPause(), the reverse applies here.
        try {
            session!!.resume()
            binding.arSceneView.resume()
        } catch (e: CameraNotAvailableException) {
            // In some cases (such as another camera app launching) the camera may be given to
            // a different app instead. Handle this properly by showing a message and recreate the
            // session at the next iteration.
            messageSnackBarHelper.showError(this, "Camera not available. Please restart the app.")
            session = null
            return
        }

    }
    private fun configureSession() {
        val config = Config(session)
        config.focusMode = Config.FocusMode.AUTO
        if (!setupAugmentedImageDb(config)) {
            messageSnackBarHelper.showError(this, "Could not setup augmented image database")
        }
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        session!!.configure(config)
    }
    private fun setupAugmentedImageDb(config: Config): Boolean {
        val augmentedImageDatabase: AugmentedImageDatabase

        val augmentedImageBitmap = loadAugmentedImage() ?: return false

        augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("qrcode", augmentedImageBitmap)

        config.augmentedImageDatabase = augmentedImageDatabase

        return true
    }
    private fun loadAugmentedImage(): Bitmap? {
        try {
            assets.open("qrcode.jpg").use { `is` -> return BitmapFactory.decodeStream(`is`) }
        } catch (e: IOException) {
            Log.e(TAG, "IO exception loading augmented image bitmap.", e)
        }

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        session?.close()

    }
}