package com.example.gallaryclone.ui.theme

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.gallaryclone.data.GallaryRepository
import com.example.gallaryclone.viewmodel.GallaryViewModel
import com.example.gallaryclone.viewmodel.ViewModelFactory

@Composable
fun GalleryScreen(viewModel: GallaryViewModel,onFolderClick: (String) -> Unit) {
    LaunchedEffect(Unit) { viewModel.loadFolders() }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.folders) { folder ->
            Column(
                Modifier
                    .padding(8.dp)
                    .clickable { onFolderClick(folder.bucketId) }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(folder.coverUri),
                    contentDescription = folder.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text("${folder.name} (${folder.itemCount})")
            }
        }
    }
}

