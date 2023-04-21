package com.avv2050soft.unsplashtool.data.api

import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos/")
    suspend fun getPhotos(
        @Header("Authorization") token : String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PER_PAGE,
        @Query("client_id") clientId: String = CLIENT_ID,
    ): List<Photo>

    @GET("photos/{id}")
    suspend fun getPhotoDetails(
        @Header("Authorization") token : String,
        @Path("id") id: String,
        @Query("client_id") clientId: String = CLIENT_ID,
    ): PhotoDetails

    companion object {
        private const val CLIENT_ID = "jDzfFlMFcQB6Z7z-7bbQsa6Om2IcKnocUGP_ci_Srgc"
        private const val BASE_URL = "https://api.unsplash.com/"
        const val PER_PAGE = 20

        fun create(): UnsplashApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

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