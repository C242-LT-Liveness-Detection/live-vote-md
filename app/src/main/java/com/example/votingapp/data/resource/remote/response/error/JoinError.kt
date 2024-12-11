package com.example.votingapp.data.resource.remote.response.error

import com.google.gson.annotations.SerializedName

data class JoinError(

    @field:SerializedName("detail")
    val detail: String
)
