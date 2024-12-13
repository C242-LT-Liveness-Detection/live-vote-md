package com.example.votingapp.presentation.features.detailVote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapp.data.repositories.VoteRepository
import com.example.votingapp.data.resource.remote.response.success.OptionsItem
import com.example.votingapp.data.resource.remote.response.success.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailVotingViewModel @Inject constructor(
    private val voteRepository: VoteRepository
) : ViewModel() {

    val votingDetail = MutableStateFlow<VoteDetail?>(null)
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)

    fun getVoteDetail(uniqueCode: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = voteRepository.voteResult(uniqueCode)
                votingDetail.value = VoteDetail(
                    title = response.eventTitle,
                    code = uniqueCode,
                    question = response.eventQuestion,
                    options = response.results,
                )
            } catch (e: HttpException) {
                errorMessage.value = when (e.code()) {
                    401 -> "Unauthorized access"
                    404 -> "Voting not found"
                    else -> "An error occurred"
                }
            } catch (e: Exception) {
                errorMessage.value = "An error occurred: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}


data class VoteDetail(
    val title: String,
    val code: String,
    val question: String,
    val options: List<ResultsItem>,
)
