package com.example.votingapp.presentation.features.choseVote

import com.example.votingapp.data.repositories.VoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChoseVoteViewModel @Inject constructor(
    private val voteRepository: VoteRepository
) {


}