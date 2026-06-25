package com.baron.badmintonapp.data.remote.response

// What POST /social/:id/register returns on success (2xx).
// Genuine failures (network error, 403 for non-player accounts, 500) should
// be handled via response.code() / response.isSuccessful in the repository,
// NOT folded into this DTO — keep "request succeeded, here's the outcome"
// separate from "request failed entirely".
data class RegistrationResponse(
    val status: RegistrationStatus,
    val registration_id: Int? = null
)