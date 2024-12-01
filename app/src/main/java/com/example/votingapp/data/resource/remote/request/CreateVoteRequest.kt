package com.example.votingapp.data.resource.remote.request

import com.google.gson.annotations.SerializedName

data class CreateVoteRequest(

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("question")
    val question: String,

    @field:SerializedName("options")
    val options: List<String>,

    @field:SerializedName("allow_multiple_votes")
    val isMultipleChoice: Boolean,

    @field:SerializedName("end_date")
    val endDate: String
)