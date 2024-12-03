package com.example.votingapp.data.repositories

import com.example.votingapp.data.resource.remote.request.CreateVoteRequest
import com.example.votingapp.data.resource.remote.request.JoinVoteRequest
import com.example.votingapp.data.resource.remote.response.success.JoinVoteResponse
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponseItem
import com.example.votingapp.data.resource.remote.response.success.VoteByCodeResponse
import com.example.votingapp.data.resource.remote.retrofit.ApiService
import com.example.votingapp.data.storage.UserPreferenceStore
import javax.inject.Inject

class VoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferenceStore: UserPreferenceStore
) {

    suspend fun getAllVoteByUser(): List<ListVoteResponseItem> {
        return apiService.getAllVoteByUser()
    }

    suspend fun createVote(
        title: String,
        question: String,
        options: List<String>,
        isMultipleChoice: Boolean,
        endDate: String,
    ) {
        val request = CreateVoteRequest(
            title = title,
            question = question,
            options = options,
            isMultipleChoice = isMultipleChoice,
            endDate = endDate,
        )
        apiService.createVote(
            request
        )
    }

    suspend fun joinVote(code: String): JoinVoteResponse {
        val request = JoinVoteRequest(
            code = code
        )
        return apiService.joinVote(request)
    }

    suspend fun getVoteByCode(code: String): VoteByCodeResponse {
        return apiService.getVoteByCode(code)
    }

}