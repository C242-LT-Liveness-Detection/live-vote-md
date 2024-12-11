package com.example.votingapp.presentation.features.liveness

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.votingapp.core.navigation.navigateToCreateVote
import com.example.votingapp.presentation.components.DialogMessage

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun LivenessRoute(
    navController: NavController,
    viewModel: LivenessViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    if (viewModel.isSuccessful.value) {
        viewModel.vibration(context)
        DialogMessage(
            message = "Berhasil",
            confirmButton = {
                TextButton(
                    onClick = { navController.navigateToCreateVote() }
                ) {
                    Text("OK")
                }
            }
        )
    }

    LivenessScreen(
        setupCamera = viewModel::setupCamera,
        lifecycleOwner = lifecycleOwner,
        context = context
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun LivenessScreen(
    setupCamera: (context: Context, previewView: PreviewView, lifecycleOwner: LifecycleOwner) -> Unit,
    lifecycleOwner: LifecycleOwner,
    context: Context


) {


    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PreviewView(ctx).also { previewView ->
                setupCamera(context, previewView, lifecycleOwner)
            }
        }
    )
}

