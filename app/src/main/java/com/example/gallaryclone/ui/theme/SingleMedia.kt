package com.example.gallaryclone.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gallaryclone.data.MediaModel

@Composable
fun SingleMedia(
    media : MediaModel,
    onBack: () -> Unit
){
    Image(
        painter = rememberAsyncImagePainter(media.uri),
        contentDescription = media.displayName,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}