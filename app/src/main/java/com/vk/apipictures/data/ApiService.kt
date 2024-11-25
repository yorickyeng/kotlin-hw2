package com.vk.apipictures.data

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "UZKSmKWKIvqVhE98UVk2kzT9MYZJ63oL"

interface ApiService {
    @GET("/v1/gifs/trending")
    suspend fun getGIF(
        @Query("key") key: String = API_KEY,
    ): GiphyResponse
}