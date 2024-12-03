package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class VoteByCodeResponse(

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("options")
	val options: List<OptionsItem>,

	@field:SerializedName("created_date")
	val createdDate: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("unique_code")
	val uniqueCode: String
)

data class OptionsItem(

	@field:SerializedName("option_text")
	val optionText: String,

	@field:SerializedName("event_option_number")
	val eventOptionNumber: Int
)
