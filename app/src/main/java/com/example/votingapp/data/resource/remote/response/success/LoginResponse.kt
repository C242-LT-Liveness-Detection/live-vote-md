package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token_type")
    val tokenType: String
)
