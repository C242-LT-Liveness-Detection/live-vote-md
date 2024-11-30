package com.example.votingapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoteModel(
    val code: String,
    val title: String,
    val question: String,
    val createdAt: String,
    val endDate: String,
) : Parcelable
