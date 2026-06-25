package com.baron.badmintonapp.data

import com.baron.badmintonapp.data.remote.response.RegistrationResponse
import com.baron.badmintonapp.data.remote.response.SocialEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SocialApi {

    @GET("social")
    suspend fun getSocialEvents(): Response<List<SocialEvent>>

    // Needs auth — the JWT must reach Express somehow. Either an OkHttp
    // interceptor on RetrofitInstance attaches "Authorization: Bearer <token>"
    // automatically to every request, or you pass it manually as a header param
    // here. Worth deciding once AuthViewModel actually persists the token somewhere.
    @POST("social/{id}/register")
    suspend fun registerForEvent(@Path("id") socialId: Int): Response<RegistrationResponse>
}