package com.example.gallaryclone.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore.Audio.Media
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gallaryclone.data.GallaryRepository
import com.example.gallaryclone.data.MediaModel
import com.example.gallaryclone.viewmodel.GallaryViewModel
import com.example.gallaryclone.viewmodel.ViewModelFactory

@Composable
fun HomeView(
    context: Context,
    viewModel: GallaryViewModel = viewModel(factory = ViewModelFactory(GallaryRepository(context), context)
    )
) {
    var currentScreen by remember { mutableStateOf("folders") }
    var selectedImage by remember { mutableStateOf<MediaModel?>(null) }

    when (currentScreen) {
        "folders" -> {
            GalleryScreen(viewModel) { bucketId ->
                viewModel.selectFolder(bucketId)
                currentScreen = "media"
            }
        }

        "media" -> {
            viewModel.selectedFolder?.let { folder ->
                MediaScreen(folder) { imageUri ->
                    selectedImage = imageUri
                    currentScreen = "fullImage"
                }
            }
        }

        "fullImage" -> {
            selectedImage?.let { Media ->
                SingleMedia(Media) {
                    currentScreen = "media"
                }
            }
        }
    }
}
