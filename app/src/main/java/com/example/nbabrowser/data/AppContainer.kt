package com.example.nbabrowser.data

import com.example.nbabrowser.BuildConfig
import com.example.nbabrowser.network.NBAApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

interface AppContainer {
    val nbaRepository: NBARepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "https://api.balldontlie.io/v1/"

    private val authClient = OkHttpClient().newBuilder()
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .header("Authorization", BuildConfig.API_KEY)
                    .build()
                chain.proceed(request)
            }
        )

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(authClient.build())
        .build()

    private val retrofitService: NBAApiService by lazy {
        retrofit.create(NBAApiService::class.java)
    }

    override val nbaRepository: NBARepository by lazy {
        NetworkNBARepository(retrofitService)
    }
}