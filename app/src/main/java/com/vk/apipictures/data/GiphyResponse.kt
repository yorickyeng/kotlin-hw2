package com.vk.apipictures.data

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @SerializedName("data")
    val data: List<Response>
)

data class Response(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val images: Image
)

data class Image(
    @SerializedName("original")
    val original: GIF
)

data class GIF(
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("hash")
    val hash: String
)