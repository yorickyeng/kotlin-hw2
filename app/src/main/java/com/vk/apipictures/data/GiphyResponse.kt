package com.vk.apipictures.data

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @SerializedName("data")
    val data: List<GIFObject>
)

data class GIFObject(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val images: ImagesObject
)

data class ImagesObject(
    @SerializedName("original")
    val original: Original
)

data class Original(
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("hash")
    val hash: String
)