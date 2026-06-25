package com.baron.badmintonapp.ui.states

import com.baron.badmintonapp.data.remote.response.SocialEvent

data class SocialUiState(
    val events: List<SocialEvent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registrationResult: RegistrationStatus? = null // e.g. CONFIRMED, WAITLISTED, ALREADY_REGISTERED
)