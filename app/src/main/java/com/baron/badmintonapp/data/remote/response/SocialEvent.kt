package com.baron.badmintonapp.data.remote.response

data class SocialEvent(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val spotsLeft: Int
)