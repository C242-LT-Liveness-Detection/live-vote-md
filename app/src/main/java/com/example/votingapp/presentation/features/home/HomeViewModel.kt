package com.example.votingapp.presentation.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.data.repositories.VoteRepository
import com.example.votingapp.data.storage.UserPreferenceStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore,
    private val voteRepository: VoteRepository
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreferenceStore.clear()
        }
    }

    fun getVotes() {
        viewModelScope.launch {
            voteRepository.getAllVoteByUser()
        }
    }
}