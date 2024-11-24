package com.example.votingapp.data.resource.remote.retrofit

import com.example.votingapp.data.resource.remote.request.LoginRequest
import com.example.votingapp.data.resource.remote.request.RegisterRequest
import com.example.votingapp.data.resource.remote.response.success.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

}