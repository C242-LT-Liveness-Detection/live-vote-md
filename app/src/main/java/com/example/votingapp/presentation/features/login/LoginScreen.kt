package com.example.votingapp.presentation.features.login

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.votingapp.R
import com.example.votingapp.core.navigation.navigateToHome
import com.example.votingapp.core.navigation.navigateToRegister
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.InputPassword
import com.example.votingapp.presentation.components.InputTextField

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val errorMessages = viewModel.errorMessages.value
    val registerUiInfo = viewModel.loginUiInfo.collectAsStateWithLifecycle().value
    LoginScreen(
        registerUiInfo,
        errorMessages,
        viewModel::login,
        viewModel::onPasswordVisibilityChanged,
        viewModel.passwordVisible.value,
        viewModel::onEmailChanged,
        viewModel::onPasswordChanged,
        viewModel.loading.value,
        viewModel::clearErrorMessages,
        viewModel.successMessage.value,
        navController = navController
    )
}

@Composable
fun LoginScreen(
    loginUiInfo: LoginUiInfo,
    errorMessages: String? = null,
    login: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    passwordVisible: Boolean = false,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    loading: Boolean = false,
    clearErrorMessages: () -> Unit = {},
    successMessage: String? = null,
    navController: NavController


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
            navController.navigateToHome()

        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        InputTextField(
            keyboardType = KeyboardType.Email,
            text = loginUiInfo.email, label = "Email"
        ) {
            onEmailChanged(it)
        }

        Spacer(modifier = Modifier.height(10.dp))
        InputPassword(
            passwordVisible = passwordVisible,
            value = loginUiInfo.password,
            togglePasswordVisibility = onPasswordVisibilityChanged,
        ) {
            onPasswordChanged(it)
        }

        Spacer(modifier = Modifier.height(10.dp))
        AppButton(text = stringResource(R.string.login), enabled = loading.not()) {
            login()

        }
        Spacer(modifier = Modifier.height(10.dp))
        RegisterText(onRegisterClick = {
            navController.navigateToRegister()
        })
    }
}

@Composable
fun RegisterText(onRegisterClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append("Belum punya akun? ")
        withLink(
            link = LinkAnnotation.Clickable(
                tag = "register",
                linkInteractionListener = { _ ->
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
            append("Daftar")
        }
    }

    BasicText(text = annotatedString)

}

