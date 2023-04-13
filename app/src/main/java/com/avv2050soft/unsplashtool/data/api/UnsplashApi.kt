package com.avv2050soft.unsplashtool.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos/")
    suspend fun getPhotos(
        @Query("client_id") clientId: String = CLIENT_ID
    )

    companion object{
        private const val CLIENT_ID = "jDzfFlMFcQB6Z7z-7bbQsa6Om2IcKnocUGP_ci_Srgc"
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): UnsplashApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashApi::class.java)
        }
    }
}