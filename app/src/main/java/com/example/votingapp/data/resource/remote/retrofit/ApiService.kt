package com.example.votingapp.data.resource.remote.retrofit

import com.example.votingapp.data.resource.remote.request.CreateVoteRequest
import com.example.votingapp.data.resource.remote.request.JoinVoteRequest
import com.example.votingapp.data.resource.remote.request.RegisterRequest
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponse
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponseItem
import com.example.votingapp.data.resource.remote.response.success.LoginResponse
import com.example.votingapp.data.resource.remote.response.success.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
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
    ): LoginResponse


    @GET("events/retrieve")
    suspend fun getAllVoteByUser(

    ): List<ListVoteResponseItem>


    @POST("events/create")
    suspend fun createVote(
        @Body createVoteRequest: CreateVoteRequest
    )

    @POST("events/join")
    suspend fun joinVote(
        @Body body: JoinVoteRequest
    )
}