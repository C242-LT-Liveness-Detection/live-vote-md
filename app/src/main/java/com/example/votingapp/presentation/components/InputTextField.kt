package com.example.votingapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.votingapp.R
import com.example.votingapp.core.ui.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    searchQuery: () -> Unit = {},
    label: String = stringResource(R.string.label),
    icon: ImageVector = Icons.Default.Email,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Done,
    enabled: Boolean = true,
    maxLine: Int = 3,
    type: InputTextFieldType = InputTextFieldType.Outlined,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,

    ) {
    when (type) {
        InputTextFieldType.Classic -> TextField(
            value = text,
            label = { Text(text = label) },
            enabled = enabled,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),

            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.extraSmall,
            placeholder = { Text(text = label) },
            maxLines = maxLine
        )

        InputTextFieldType.Outlined -> OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            label = { Text(label) },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            maxLines = maxLine,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon

        )


        InputTextFieldType.WithIcon -> OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                )
            },
            label = { Text(label) },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),

            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            maxLines = maxLine
        )

        InputTextFieldType.IconClickable -> TODO()
    }
}

@Preview
@Composable
fun PreviewOutlinedTextField() {
    AppTheme {
        InputTextField(text = "Outlined", type = InputTextFieldType.Outlined) {}
    }
}

@Preview
@Composable
fun PreviewClassicTextField() {
    AppTheme {
        InputTextField(text = "Classic", type = InputTextFieldType.Classic) {}
    }
}

@Preview
@Composable
fun PreviewWithIconTextField() {
    AppTheme {
        InputTextField(
            text = "With Icon",
            type = InputTextFieldType.WithIcon,
            icon = Icons.Default.Search
        ) {}
    }
}

enum class InputTextFieldType {
    Classic, Outlined, WithIcon, IconClickable
}