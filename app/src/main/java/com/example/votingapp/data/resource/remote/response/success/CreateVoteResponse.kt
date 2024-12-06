package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class CreateVoteResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("event")
	val event: String,

	@field:SerializedName("unique_code")
	val uniqueCode: String
)
