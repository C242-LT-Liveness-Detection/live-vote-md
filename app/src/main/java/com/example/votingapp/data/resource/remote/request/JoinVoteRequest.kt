package com.example.votingapp.data.resource.remote.request

import com.google.gson.annotations.SerializedName

data class JoinVoteRequest(
    @field:SerializedName("unique_code")
    val code: String
)
