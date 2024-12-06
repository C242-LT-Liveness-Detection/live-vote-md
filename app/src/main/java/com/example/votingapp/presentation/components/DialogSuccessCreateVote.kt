package com.example.votingapp.presentation.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.votingapp.R
import com.example.votingapp.core.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DialogSuccessCreateVote(
    dialogType: DialogType = DialogType.SUCCESS,
    code: String,
    dismissButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (() -> Unit)? = null,
) {
    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.success_animation)
    )
    val localClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,

        )

    BasicAlertDialog(

        onDismissRequest = {

        },
        properties = DialogProperties(

        ),
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LottieAnimation(
                    composition = preloaderLottieComposition.value,
                    progress = preloaderProgress,
                    modifier = Modifier
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text =
                    "Voting berhasil dibuat bagikan Code Vote ke orang lain",
                )
                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                        .height(50.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            code,
                            style = MaterialTheme.typography.bodyMedium,

                            )

                        IconButton(
                            onClick = {
                                localClipboardManager.setText(AnnotatedString(code))
                                Toast.makeText(
                                    context,
                                    "Code Vote berhasil disalin",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },

                            ) {
                            Icon(
                                Icons.Filled.ContentCopy,
                                contentDescription = "Copy Code Vote",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }



                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    dismissButton?.let {
                        it()
                    }
                    confirmButton?.let {
                        it()
                    }
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable

fun DialogSuccessCreateVotePreview() {
    AppTheme {
        DialogSuccessCreateVote(code = "sdasdasd", dismissButton = {}, confirmButton = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Oke")
            }

        })
    }
}