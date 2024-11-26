package com.vk.apipictures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.vk.apipictures.data.API_KEY
import com.vk.apipictures.data.RetrofitInstance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyAPI()
        }
    }
}

@Composable
fun GiphyAPI() {
    val list = remember { mutableStateListOf<String>() }
    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(isError) {
        if (list.isEmpty()) {
            try {
                val response = RetrofitInstance.api.getGIF(API_KEY, 10, 0)
                for (item in response.data) {
                    list.add(item.images.original.url)
                }
            } catch (e: Exception) {
                errorMessage = "${e.message}"
                isError = true
            } finally {
                isLoading = false
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF9999))
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (isError) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = errorMessage,
                    fontSize = 32.sp,
                    color = Color.Red,
                )
                Button(
                    onClick = {
                        isError = false
                    }
                ) { Text(text = "Try Again") }
            }
        } else {
            LazyVerticalStaggeredGrid(list)
        }
    }
}

@Composable
fun LazyVerticalStaggeredGrid(list: MutableList<String>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(list) { url ->
                GlideListBlock(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun GlideListBlock(url: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .border(
                1.5.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(12.dp)
            )
    )
    {
        GlideImage(
            imageModel = { url },
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            failure = {
                val rememberedUrl = remember { mutableStateOf(url) }
                Button(
                    onClick = { rememberedUrl.value = url },
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxSize(),
                ) { Text(text = "Reload") }
            },
        )
    }
}