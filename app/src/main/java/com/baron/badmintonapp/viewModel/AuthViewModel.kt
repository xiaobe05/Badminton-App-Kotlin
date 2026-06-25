package com.baron.badmintonapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baron.badmintonapp.data.RetrofitInstance
import com.baron.badmintonapp.request.LoginRequest
import com.baron.badmintonapp.ui.states.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())

    fun getUiState():StateFlow<AuthUiState>{
        return _uiState.asStateFlow()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val response = RetrofitInstance.authApi.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Invalid email or password")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Network error")
            }
        }
    }
}