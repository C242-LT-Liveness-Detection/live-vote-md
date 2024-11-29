package com.example.votingapp.data.repositories

import com.example.votingapp.data.resource.remote.response.success.ListVoteResponse
import com.example.votingapp.data.resource.remote.response.success.ListVoteResponseItem
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
}