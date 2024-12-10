package com.example.votingapp.presentation.features.liveness

import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun CameraScreen(onPreviewReady: (PreviewView) -> Unit, guideText: String = "") {
    Column(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            modifier = Modifier.weight(1f),
            onPreviewReady = onPreviewReady
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Silahkan menghadap ke Kiri",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun CameraPreview(modifier: Modifier = Modifier, onPreviewReady: (PreviewView) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context)
            onPreviewReady(previewView)
            previewView
        }
    )
}
