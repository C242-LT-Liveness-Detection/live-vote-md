package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: String
)
