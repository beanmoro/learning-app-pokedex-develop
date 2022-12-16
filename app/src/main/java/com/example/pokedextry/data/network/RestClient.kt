package com.example.pokedextry.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RestClient {

    private const val CONNECT_TIMEOUT = 30
    private const val READ_TIMEOUT = 60
    private const val BASE_URL = "https://pokeapi.co/"
    private const val PATH = "api/v2/"


    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    val instance : API by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL + PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(API::class.java)
    }
}