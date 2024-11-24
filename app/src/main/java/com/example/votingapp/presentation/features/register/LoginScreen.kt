package com.example.votingapp.presentation.features.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.InputTextField
import androidx.compose.ui.text.withLink
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.votingapp.R
import com.example.votingapp.presentation.components.InputPassword

@Composable
internal fun RegisterRoute(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val errorMessages = viewModel.errorMessages.value
    val registerUiInfo = viewModel.registerUiInfo.collectAsStateWithLifecycle().value
    RegisterScreen(
        modifier,
        registerUiInfo,
        errorMessages,
        viewModel::register,
        viewModel::onPasswordVisibilityChanged,
        viewModel::onNameChanged,
        viewModel::onEmailChanged,
        viewModel::onPasswordChanged,
        viewModel.passwordVisible.value,
        viewModel.loading.value,
        viewModel::clearErrorMessages,
        viewModel.successMessage.value
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerUiInfo: RegisterUiInfo,
    errorMessages: String? = null,
    register: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    passwordVisible: Boolean = false,
    loading: Boolean = false,
    clearErrorMessages: () -> Unit = {},
    successMessage: String? = null,


    ) {
    val context = LocalContext.current
    LaunchedEffect(errorMessages) {
        if (errorMessages != null) {
            Toast.makeText(context, errorMessages, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
        }

    }

    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        InputTextField(text = registerUiInfo.email, label = "Email") {
            onEmailChanged(it)
        }
        Spacer(modifier = Modifier.height(10.dp))

        InputTextField(text = registerUiInfo.name, label = "Nama") {
            onNameChanged(it)
        }
        Spacer(modifier = Modifier.height(10.dp))
        InputPassword(
            passwordVisible = passwordVisible,
            value = registerUiInfo.password,
            togglePasswordVisibility = onPasswordVisibilityChanged,
        ) {
            onPasswordChanged(it)
        }

        Spacer(modifier = Modifier.height(10.dp))
        AppButton(text = stringResource(R.string.register), enabled = loading.not()) {
            Log.d("RegisterViewModel", "register: halo")
            register()

        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginText(onRegisterClick = {
//            navigateToLogin()
        })
    }
}

@Composable
fun LoginText(onRegisterClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append("Sudah punya akun? ")
        withLink(
            link = LinkAnnotation.Clickable(
                tag = "login",
                linkInteractionListener = { offset ->
                    onRegisterClick()
                },
                styles = TextLinkStyles(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                )
            )
        ) {
            append("Masuk")
        }
    }

    BasicText(text = annotatedString)

}

