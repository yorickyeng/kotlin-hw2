package com.vk.apipictures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vk.apipictures.data.RetrofitInstance


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyAPI()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiphyAPI() {
    val list = remember { mutableStateListOf("") }

    LaunchedEffect(Unit) {
        val response = RetrofitInstance.api.getGIF()
        for (item in response.data) {
            list.add(item.images.original.url)
        }
    }

    LazyColumn {
        items(list) { url ->
            GlideImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }

    }
}