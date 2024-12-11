package com.example.votingapp.presentation.features.liveness

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.votingapp.core.navigation.navigateToChoseVote
import com.example.votingapp.core.navigation.navigateToCreateVote
import com.example.votingapp.core.ui.AppTheme
import com.example.votingapp.presentation.components.DialogMessage

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun LivenessRoute(
    navController: NavController,
    voteCode: String,
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
                    onClick = { navController.navigateToChoseVote(voteCode) }
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

    Column {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            factory = { ctx ->
                PreviewView(ctx).also { previewView ->
                    setupCamera(context, previewView, lifecycleOwner)
                }
            }
        )


        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Verifikasi diri anda!", style = MaterialTheme.typography.headlineSmall)
        }
    }

}

