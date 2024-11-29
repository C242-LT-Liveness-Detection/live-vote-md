package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class ListVoteResponse(

	@field:SerializedName("ListVoteResponse")
	val listVoteResponse: List<ListVoteResponseItem>
)

data class ListVoteResponseItem(

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("created_date")
	val createdDate: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("unique_code")
	val uniqueCode: String
)
