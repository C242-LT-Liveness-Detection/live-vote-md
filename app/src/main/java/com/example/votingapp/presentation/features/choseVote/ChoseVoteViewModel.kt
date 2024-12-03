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


    fun getVoteByCode(uniqueCode: String) {
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

}