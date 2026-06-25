package com.baron.badmintonapp.data.repository

import com.baron.badmintonapp.data.RetrofitInstance
import com.baron.badmintonapp.data.SocialApi
import com.baron.badmintonapp.data.remote.response.RegistrationResponse
import com.baron.badmintonapp.data.remote.response.SocialEvent

// Default param means production code never has to pass anything, but tests
// can construct SocialRepository(fakeSocialApi) without touching the real network.
class SocialRepository(
    private val socialApi: SocialApi = RetrofitInstance.socialApi
) {
    suspend fun getEvents(): Result<List<SocialEvent>> {
        return try {
            val response = socialApi.getSocialEvents()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to load events (code ${response.code()})"))
            }
        } catch (e: Exception) {
            // Covers no internet, timeout, etc. — anything that never even reached the server.
            Result.failure(e)
        }
    }

    suspend fun registerForEvent(socialId: Int): Result<RegistrationResponse> {
        return try {
            val response = socialApi.registerForEvent(socialId)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("Registration failed (code ${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}