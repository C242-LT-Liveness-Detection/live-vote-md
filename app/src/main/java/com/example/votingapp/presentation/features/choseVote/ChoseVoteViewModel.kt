package com.example.votingapp.presentation.features.choseVote

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.data.repositories.VoteRepository
import com.example.votingapp.data.resource.remote.response.success.VoteByCodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChoseVoteViewModel @Inject constructor(
    private val voteRepository: VoteRepository
) : ViewModel() {

    val loading = mutableStateOf(true)
    val successMessage = mutableStateOf<String?>(null)
    val errorMessages = mutableStateOf<String?>(null)
    val vote = mutableStateOf<VoteByCodeResponse?>(null)
    val selectedOption = mutableStateOf<List<Int>>(emptyList())

    fun getVoteByCode(uniqueCode: String) {
        Log.d("ChoseVoteViewModel", "getVoteByCode: $uniqueCode")
        viewModelScope.launch {
            try {
                val response = voteRepository.getVoteByCode(uniqueCode)
                vote.value = response
                loading.value = false

            } catch (e: Exception) {
                errorMessages.value = "An error occurred"
                Log.e("ChoseVoteViewModel", "getVoteByCode: ${e.message}")
            } catch (e: HttpException) {
                errorMessages.value = e.message()
                Log.e("ChoseVoteViewModel", "getVoteByCode: ${e.message()}")
            }

        }
    }


    fun onEvent(event: ChoseVoteEvent) {
        when (event) {
            is ChoseVoteEvent.SelectOption -> {
                if (vote.value!!.allowMultipleVotes) {
                    selectedOption.value += event.option
                } else {
                    selectedOption.value = listOf(event.option)
                }
            }

            is ChoseVoteEvent.RemoveOption -> {
                selectedOption.value -= event.option
            }

            is ChoseVoteEvent.SubmitVote -> {
                Log.d("ChoseVoteViewModel", "submitVote: ${selectedOption.value}")
                viewModelScope.launch {
                    try {
                        voteRepository.submitVote(event.code, selectedOption.value)
                        successMessage.value = "Vote submitted successfully"
                    } catch (e: Exception) {
                        errorMessages.value = "An error occurred"
                        Log.e("ChoseVoteViewModel", "submitVote: ${e.message}")
                    } catch (e: HttpException) {
                        errorMessages.value = e.message()
                        Log.e("ChoseVoteViewModel", "submitVote: ${e.message()}")
                    }
                }
            }

            is ChoseVoteEvent.ClearError -> {
                errorMessages.value = null
            }

            is ChoseVoteEvent.ClearSuccess -> {
                successMessage.value = null
            }
        }
    }
}

sealed interface ChoseVoteEvent {
    data class SelectOption(val option: Int) : ChoseVoteEvent
    data class RemoveOption(val option: Int) : ChoseVoteEvent
    data class SubmitVote(val code: String) : ChoseVoteEvent
    data object ClearError : ChoseVoteEvent
    data object ClearSuccess : ChoseVoteEvent
}