package com.example.votingapp.presentation.features.joinVote

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.data.repositories.VoteRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


@HiltViewModel
class JoinVotingViewModel @Inject constructor(
    private val voteRepository: VoteRepository
) : ViewModel() {

    val code = mutableStateOf("")

    private fun joinVote() {
        viewModelScope.launch {
            try {

                voteRepository.joinVote(code = code.value)
            } catch (e: Exception) {
                Log.e("JoinVotingViewModel", "joinVote: ${e.message}")
            }
        }
    }

    fun onEvent(event: JoinVoteEvent) {
        when (event) {
            is JoinVoteEvent.InputOnChanged -> {
                code.value = event.code
            }

            is JoinVoteEvent.JoinVote -> {
                joinVote()
            }
        }
    }

}


sealed interface JoinVoteEvent {
    data class InputOnChanged(val code: String) : JoinVoteEvent
    data object JoinVote : JoinVoteEvent
}