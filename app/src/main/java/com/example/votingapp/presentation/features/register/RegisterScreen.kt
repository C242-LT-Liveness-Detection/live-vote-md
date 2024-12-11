package com.example.votingapp.presentation.features.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.votingapp.core.navigation.loginNavigationRoute
import com.example.votingapp.presentation.components.AppButton
import com.example.votingapp.presentation.components.DialogMessage
import com.example.votingapp.presentation.components.DialogType
import com.example.votingapp.presentation.components.InputPassword
import com.example.votingapp.presentation.components.InputTextField

@Composable
internal fun RegisterRoute(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val errorMessages = viewModel.errorMessages.value
    val successMessage = viewModel.successMessage.value
    val registerUiInfo = viewModel.registerUiInfo.collectAsStateWithLifecycle().value

    if (successMessage != null) {
        DialogMessage(
            message = successMessage,
            confirmButton = {
                TextButton(
                    onClick = {
                        navController.navigate(loginNavigationRoute) {
                            popUpTo(loginNavigationRoute) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Oke")
                }
            }
        )
    }

    if (errorMessages != null) {
        DialogMessage(
            dialogType = DialogType.ERROR,
            message = errorMessages,
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearErrorMessages()
                    }
                ) {
                    Text("Oke")
                }
            }
        )
    }

    RegisterScreen(
        registerUiInfo,
        viewModel::register,
        viewModel::onPasswordVisibilityChanged,
        viewModel::onNameChanged,
        viewModel::onEmailChanged,
        viewModel::onPasswordChanged,
        viewModel.passwordVisible.value,
        viewModel.loading.value,
        navController = navController
    )
}

@Composable
fun RegisterScreen(
    registerUiInfo: RegisterUiInfo,
    register: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    passwordVisible: Boolean = false,
    loading: Boolean = false,
    navController: NavController


) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        InputTextField(
            keyboardType = KeyboardType.Email,
            text = registerUiInfo.email, label = "Email"
        ) {
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
            navController.navigate(loginNavigationRoute) {
                popUpTo(loginNavigationRoute) {
                    inclusive = true
                }
            }
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
            append("Masuk")
        }
    }

    BasicText(text = annotatedString)

}

