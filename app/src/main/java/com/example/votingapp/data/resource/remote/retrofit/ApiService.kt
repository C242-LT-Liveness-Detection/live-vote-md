package com.example.votingapp.data.resource.remote.retrofit

import com.example.votingapp.data.resource.remote.request.CreateVoteRequest
import com.example.votingapp.data.resource.remote.request.JoinVoteRequest
import com.example.votingapp.data.resource.remote.request.RegisterRequest
import com.example.votingapp.data.resource.remote.request.SubmitVoteRequest
import com.example.votingapp.data.resource.remote.response.success.CreateVoteResponse
import com.example.votingapp.data.resource.remote.response.success.JoinVoteResponse
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponse
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponseItem
import com.example.votingapp.data.resource.remote.response.success.LoginResponse
import com.example.votingapp.data.resource.remote.response.success.RegisterResponse
import com.example.votingapp.data.resource.remote.response.success.VoteByCodeResponse
import com.example.votingapp.data.resource.remote.response.success.VoteResultResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

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
    ): CreateVoteResponse

    @POST("events/join")
    suspend fun joinVote(
        @Body body: JoinVoteRequest
    ): JoinVoteResponse

    @GET("events/{code}")
    suspend fun getVoteByCode(
        @Path("code") code: String
    ): VoteByCodeResponse

    @POST("/events/cast-vote")
    suspend fun submitVote(
        @Body body: SubmitVoteRequest
    )

    @POST("events/result")
    suspend fun voteResult(
        @Body body: JoinVoteRequest
    ): VoteResultResponse
}