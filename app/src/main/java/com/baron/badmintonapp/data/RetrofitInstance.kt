package com.baron.badmintonapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // IMPORTANT — pick the right base URL for where you're running the app:
    // - Android Emulator  → "http://10.0.2.2:3000/"      (10.0.2.2 is the emulator's alias for your PC's localhost)
    // - Physical device    → "http://<your-PC-LAN-IP>:3000/"  (e.g. 192.168.1.42 — find via `ipconfig`, phone+PC must be on same Wi-Fi)
    // Port must match whatever your Express server actually listens on (check index.js / npm run dev output).
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // Logs full request/response bodies to Logcat — extremely useful for seeing exactly
    // what JSON your Android app sent vs what Express actually replied with.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    val socialApi: SocialApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SocialApi::class.java)
    }
}