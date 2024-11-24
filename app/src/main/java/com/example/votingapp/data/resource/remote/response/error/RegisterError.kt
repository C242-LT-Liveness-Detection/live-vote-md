package com.example.votingapp.data.resource.remote.response.error

import com.google.gson.annotations.SerializedName

data class RegisterError(
    @field:SerializedName("detail")
    val message: String

)