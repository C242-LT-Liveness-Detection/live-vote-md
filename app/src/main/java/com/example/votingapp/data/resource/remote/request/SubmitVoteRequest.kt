package com.example.votingapp.data.resource.remote.request

import com.google.gson.annotations.SerializedName

data class SubmitVoteRequest(
    @field:SerializedName("unique_code")
    val uniqueCode: String,

    @field:SerializedName("event_option_numbers")
    val eventOptionNumbers: List<Int>

)
