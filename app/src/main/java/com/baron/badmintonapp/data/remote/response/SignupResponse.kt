package com.baron.badmintonapp.response

data class SignupResponse(
    val account_id: Int,
    val account_name: String,
    val account_email: String,
    val account_type_id: Int
)
