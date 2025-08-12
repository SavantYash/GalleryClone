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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    val navController = rememberNavController()

    NavHost(navController, startDestination = "folders") {
        composable("folders") {
            GalleryScreen(viewModel) { bucketId ->
                viewModel.selectFolder(bucketId)
                navController.navigate("media")
            }
        }
        composable("media") {
            viewModel.selectedFolder?.let {
                MediaScreen(it) { imageUri ->
                    navController.navigate("singlescreen/${imageUri}")
                }
            }
        }
        composable("singlescreen/{imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) {
            backStackEntry ->
            val uri = backStackEntry.arguments?.getString("imageUri")
            SingleMedia(uri!!)
        }
    }
}
