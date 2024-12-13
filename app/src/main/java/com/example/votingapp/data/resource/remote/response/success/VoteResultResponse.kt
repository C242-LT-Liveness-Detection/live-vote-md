package com.example.votingapp.data.resource.remote.response.success

import com.google.gson.annotations.SerializedName

data class VoteResultResponse(

	@field:SerializedName("total_votes")
	val totalVotes: Int,

	@field:SerializedName("most_voted_option")
	val mostVotedOption: String,

	@field:SerializedName("event_question")
	val eventQuestion: String,

	@field:SerializedName("event_title")
	val eventTitle: String,

	@field:SerializedName("results")
	val results: List<ResultsItem>
)

data class ResultsItem(

	@field:SerializedName("votes")
	val votes: Int,

	@field:SerializedName("option")
	val option: String
)
