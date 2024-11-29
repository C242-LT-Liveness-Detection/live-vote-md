package com.example.votingapp.data.repositories

import com.example.votingapp.data.resource.remote.request.RegisterRequest
import com.example.votingapp.data.resource.remote.response.success.LoginResponse
import com.example.votingapp.data.resource.remote.response.success.RegisterResponse
import com.example.votingapp.data.resource.remote.retrofit.ApiService
import com.example.votingapp.data.storage.UserPreferenceStore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferenceStore: UserPreferenceStore
) {

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        val request = RegisterRequest(email, name, password)

        return apiService.register(request)

    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }


    suspend fun saveAccessToken(token: String) {
        userPreferenceStore.saveAccessToken(token)
    }

}