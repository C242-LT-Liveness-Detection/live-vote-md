package com.example.votingapp.presentation.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.core.domain.models.VoteModel
import com.example.votingapp.data.repositories.VoteRepository
import com.example.votingapp.data.storage.UserPreferenceStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore,
    private val voteRepository: VoteRepository
) : ViewModel() {

    val listVotes = MutableStateFlow(listOf<VoteModel>())
    val errorMessage = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)

    fun logout() {
        viewModelScope.launch {
            userPreferenceStore.clear()
        }
    }

    fun getVotes() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val votes = voteRepository.getAllVoteByUser()
                Log.d("HomeViewModel", "wkwk mantap: $votes")
                listVotes.value = votes.map {
                    VoteModel(
                        it.uniqueCode,
                        it.title,
                        it.question,
                        it.createdDate,
                        it.endDate
                    )
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> {
                        errorMessage.value = "Unauthorized"
                    }

                    404 -> {
                        listVotes.value = listOf()

                    }

                    else -> {
                        errorMessage.value = "Something went wrong"
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = "Something went wrong"
            } finally {

                isLoading.value = false
            }
        }
    }
}