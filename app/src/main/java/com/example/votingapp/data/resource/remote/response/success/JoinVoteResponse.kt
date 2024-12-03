package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class JoinVoteResponse(

	@field:SerializedName("message")
	val message: String
)
