package com.example.votingapp.presentation.features.liveness

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.core.utils.ImageClassifierHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.concurrent.Executors

class LivenessViewModel : ViewModel() {
    val isSuccessful = mutableStateOf(false)


    private var imageClassifierHelper: ImageClassifierHelper? = null
    private var isFacingCorrectly = false
    private var facingStartTime: Long = 0
    private val cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    fun setupCamera(context: Context, previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        imageClassifierHelper = ImageClassifierHelper(
            context = context,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.e("LivenessViewModel", error)
                }

                @RequiresApi(Build.VERSION_CODES.S)
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    processResults(results)
                }
            }
        )

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

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
                Log.e("CameraViewModel", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun processResults(results: List<Classifications>?) {
        results?.let {
            val isUserFacingCorrectly = it.any { classification ->
                classification.categories.any { category ->
                    Log.d(
                        "LivenessViewModel",
                        "User facing correctly: ${category.label} ${category.score}"
                    )
                    category.score > 0.9
                }
            }


            if (isUserFacingCorrectly) {
                if (!isFacingCorrectly) {
                    isFacingCorrectly = true
                    facingStartTime = System.currentTimeMillis()
                } else if (System.currentTimeMillis() - facingStartTime >= 1000) {
                    facingStartTime = 0
                    isFacingCorrectly = false
                    isSuccessful.value = true

                }
            } else {
                isFacingCorrectly = false
                facingStartTime = 0
            }
        }
    }


    fun vibration(context: Context) {
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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