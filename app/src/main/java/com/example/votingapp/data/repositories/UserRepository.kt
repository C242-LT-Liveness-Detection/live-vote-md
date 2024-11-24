package com.example.votingapp.data.repositories

import com.example.votingapp.data.resource.remote.request.LoginRequest
import com.example.votingapp.data.resource.remote.request.RegisterRequest
import com.example.votingapp.data.resource.remote.response.success.RegisterResponse
import com.example.votingapp.data.resource.remote.retrofit.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        val request = RegisterRequest(email, name, password)
        return apiService.register(request)

    }

    suspend fun loginUser(email: String, password: String): RegisterResponse {
        val request = LoginRequest(email, password)
        return apiService.login(email, password)
    }


}