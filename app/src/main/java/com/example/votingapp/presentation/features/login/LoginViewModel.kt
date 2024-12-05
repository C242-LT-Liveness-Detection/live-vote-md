package com.example.votingapp.presentation.features.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.data.repositories.UserRepository
import com.example.votingapp.data.resource.remote.response.error.LoginError
import com.example.votingapp.data.resource.remote.response.error.RegisterError
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val loginUiInfo by lazy {
        MutableStateFlow(
            LoginUiInfo("", "")
        )
    }

    val errorMessages = mutableStateOf<String?>(null)

    var passwordVisible = mutableStateOf(false)
    var loading = mutableStateOf(false)
    var successMessage = mutableStateOf<String?>(null)
    fun login() {

        viewModelScope.launch {
            loading.value = true
            try {
                val response = userRepository.loginUser(
                    loginUiInfo.value.email,
                    loginUiInfo.value.password
                )
                userRepository.saveAccessToken(response.accessToken)
                withContext(Dispatchers.Main) {
                    successMessage.value = "Login Success"
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginError::class.java)
                Log.d("RegisterViewModel", "register: ${e}")
                errorMessages.value = "ada yang error"
            } catch (e: Exception) {
                errorMessages.value = "An error occurred"
                Log.d("RegisterViewModel", "register: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

    fun onEmailChanged(email: String) {
        loginUiInfo.value = loginUiInfo.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        loginUiInfo.value = loginUiInfo.value.copy(password = password)
    }

    fun onPasswordVisibilityChanged() {
        passwordVisible.value = !passwordVisible.value
    }


    fun clearErrorMessages() {
        errorMessages.value = null
        successMessage.value = null
    }

}

data class LoginUiInfo(
    val email: String,
    val password: String
)