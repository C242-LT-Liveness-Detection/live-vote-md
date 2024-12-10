package com.example.votingapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.core.utils.ImageClassifierHelper
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.features.liveness.CameraScreen
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var imageClassifierHelper: ImageClassifierHelper? = null

    private var isFacingCorrectly = false
    private var facingStartTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val isSuccessful = remember { mutableStateOf(false) }

            AppTheme {
                if (isSuccessful.value) {
                    DialogMessage(
                        message = "Berhasil",
                        confirmButton = {
                            TextButton(
                                onClick = { finish() }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
                CameraScreen(
                    onPreviewReady = { previewView ->
                        setupCamera(previewView, lifecycleOwner, isSuccessful)
                    },

                    )
            }
        }
    }

    private fun setupCamera(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        isSuccessful: MutableState<Boolean>
    ) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                @RequiresApi(Build.VERSION_CODES.S)
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let {
                            val isUserFacingCorrectly = it.any { classification ->
                                Log.d(
                                    "CameraActivity",
                                    "User facing correctly: ${classification.categories[0].label} ${classification.categories[0].score}"
                                )
                                classification.categories.any { category ->
                                    category.label == "Left" && category.score > 0.9
                                }
                            }
                            Log.d("CameraActivity", "User facing correctly: $isUserFacingCorrectly")

                            if (isUserFacingCorrectly) {
                                if (!isFacingCorrectly) {
                                    isFacingCorrectly = true
                                    facingStartTime = System.currentTimeMillis()
                                } else if (System.currentTimeMillis() - facingStartTime >= 2000) {
                                    facingStartTime = 0
                                    isFacingCorrectly = false
                                    vibration()
                                    isSuccessful.value = true


                                }
                            } else {
                                isFacingCorrectly = false
                                facingStartTime = 0
                            }
                        }
                    }
                }
            }
        )

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build().apply {
                    setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                        imageClassifierHelper?.classifyImage(image)
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e("CameraActivity", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun vibration() {
        val vibrator =
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                ) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(500) // Vibrate method for below API Level 26
            }
        }
    }
}




