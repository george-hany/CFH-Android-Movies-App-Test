package com.core.network

import com.core.preference.sharedPref.SharedPref
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitClient
    @Inject
    constructor(private val pref: SharedPref) {
        private val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private val authInterceptor =
            Interceptor { chain ->
                val newUrl =
                    chain.request()
                        .url
                        .newBuilder()
                        .build()

                val newRequest =
                    chain.request()
                        .newBuilder()
                        .url(newUrl)
                        .addHeader("accept", "application/json")
                        .addHeader("content-type", "application/json")
                        .addHeader("accept-language", pref.getLang())
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5N2I5M2E4YmRmMjQ4NjMwZmIxMTA3NzczYmZkZjc4YyIsIm5iZiI6MTcyMTgxNjM2Ny41NzQ1NzMsInN1YiI6IjVkMjJmYTQyOTRkOGE4MjAyMDNjOGIyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rsbV5t8MrTZLphsbH5nXZN0ak263a3k-LzN50zmP_Os")
                        .build()

                chain.proceed(newRequest)
            }

        private val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        private val client =
            if (BuildConfig.DEBUG) {
                OkHttpClient().newBuilder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .build()
            }

        fun retrofit(baseUrl: String): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
