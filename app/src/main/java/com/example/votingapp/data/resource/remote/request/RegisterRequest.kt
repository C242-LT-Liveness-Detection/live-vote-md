package com.example.votingapp.data.resource.remote.request

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)
