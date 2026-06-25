package com.baron.badmintonapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baron.badmintonapp.data.repository.SocialRepository
import com.baron.badmintonapp.ui.states.SocialUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SocialViewModel(private val repository: SocialRepository = SocialRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(SocialUiState())

    fun getUiState():StateFlow<SocialUiState>{
        return _uiState.asStateFlow()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            repository.getEvents()
                .onSuccess { events ->
                    _uiState.value = _uiState.value.copy(isLoading = false, events = events)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load events"
                    )
                }
        }
    }

    fun registerForEvent(socialId: Int) {
        viewModelScope.launch {
            repository.registerForEvent(socialId)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(registrationResult = response.status)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = e.message ?: "Registration failed"
                    )
                }
        }
    }

    // Call after the UI has reacted to a result (e.g. shown a snackbar),
    // so the same result doesn't re-trigger the effect on the next recomposition.
    fun clearRegistrationResult() {
        _uiState.value = _uiState.value.copy(registrationResult = null)
    }
}