package com.example.votingapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputPassword(
    passwordVisible: Boolean,
    value: String,
    togglePasswordVisibility: () -> Unit,
    onValueChange: (String) -> Unit
) {
    InputTextField(
        text = value,
        label = "Password",
        keyboardType = KeyboardType.Password,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {

            val image = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.Visibility
            }


            IconButton(
                onClick = { togglePasswordVisibility() },
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = "Visibility",
                )
            }
        },
        onValueChange = onValueChange
    )
}