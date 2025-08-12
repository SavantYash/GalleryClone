package com.example.gallaryclone.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gallaryclone.data.Folder
import com.example.gallaryclone.data.MediaModel

@Composable
fun MediaScreen(folder: Folder,onImageClick: (MediaModel) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(folder.items) { item ->
            Image(
                painter = rememberAsyncImagePainter(item.uri),
                contentDescription = item.displayName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .aspectRatio(1f)
                    .clickable{(onImageClick(item))},
                contentScale = ContentScale.Crop
            )
        }
    }
}
