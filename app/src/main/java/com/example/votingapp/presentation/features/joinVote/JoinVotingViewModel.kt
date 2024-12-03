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
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class JoinVotingViewModel @Inject constructor(
    private val voteRepository: VoteRepository
) : ViewModel() {

    val code = mutableStateOf("KA91G")
    val errorMessage = mutableStateOf<String?>(null)
    val successMessage = mutableStateOf<String?>(null)

    private fun joinVote() {
        viewModelScope.launch {
            try {

                val response = voteRepository.joinVote(code = code.value)
                successMessage.value = "Yeay, Berhasil"
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    errorMessage.value = "Ups, Kode yang anda masukkan salah"
                }
                Log.e("JoinVotingViewModel", "joinVote: ${e.message()}")
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

            is JoinVoteEvent.ClearError -> {
                errorMessage.value = null
            }

            is JoinVoteEvent.ClearSuccess -> {
                successMessage.value = null
            }
        }
    }

}


sealed interface JoinVoteEvent {
    data class InputOnChanged(val code: String) : JoinVoteEvent
    data object JoinVote : JoinVoteEvent
    data object ClearError : JoinVoteEvent
    data object ClearSuccess : JoinVoteEvent

}