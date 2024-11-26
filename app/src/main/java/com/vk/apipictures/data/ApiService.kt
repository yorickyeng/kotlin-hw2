package com.vk.apipictures.data

import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "UZKSmKWKIvqVhE98UVk2kzT9MYZJ63oL"

interface ApiService {
    @GET("/v1/gifs/trending")
    suspend fun getGIF(
        @Query("key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): GiphyResponse
}