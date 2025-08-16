package com.example.gallaryclone.ui.theme

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter

@Composable
fun SingleMedia(
    uri : String,
    context: Context = LocalContext.current
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (uri != null) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
                Button(onClick = {
                    setWallpaperFromUri(context, uri.toUri())},
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                    Text("Set as Background")
                }
        } else {
            Text(
                text = "Image not found",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@SuppressLint("MissingPermission")
fun setWallpaperFromUri(context: Context, imageUri: Uri) {
    try {
        val wallpaperManager = WallpaperManager.getInstance(context)

        val inputStream = context.contentResolver.openInputStream(imageUri)

        val bitmap = BitmapFactory.decodeStream(inputStream)

        wallpaperManager.setBitmap(bitmap)

        Toast.makeText(context, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
        Log.d("error123", "setWallpaperFromUri: "+e.toString())
    }
}
