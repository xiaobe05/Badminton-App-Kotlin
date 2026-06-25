package com.baron.badmintonapp.data

import com.baron.badmintonapp.request.LoginRequest
import com.baron.badmintonapp.request.SignupRequest
import com.baron.badmintonapp.response.LoginResponse
import com.baron.badmintonapp.response.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    // Returns Response<T> rather than T directly, on purpose: your backend uses
    // meaningful status codes (409 = email taken, 401 = bad credentials, 500 = server error)
    // and Response<T> lets the repository layer check response.code() / response.isSuccessful
    // instead of just getting an exception thrown with no way to tell *why* it failed.
    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}