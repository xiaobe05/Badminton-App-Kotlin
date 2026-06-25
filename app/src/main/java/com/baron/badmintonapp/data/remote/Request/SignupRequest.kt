package com.baron.badmintonapp.request

data class SignupRequest(
    val account_name: String,
    val account_email: String,
    val account_password: String,
    val account_type_id: Int
)