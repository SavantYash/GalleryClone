package com.example.gallaryclone.viewmodel

import android.content.Context
import android.media.browse.MediaBrowser.MediaItem
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gallaryclone.data.Folder
import com.example.gallaryclone.data.GallaryRepository
import com.example.gallaryclone.data.MediaModel

class GallaryViewModel(private val repo : GallaryRepository, val context : Context) : ViewModel() {

    var folders by mutableStateOf<List<Folder>>(emptyList())
        private set

    var selectedFolder by mutableStateOf<Folder?>(null)
        private set

    fun loadFolders() {
        folders = repo.getFolders()
        folders.forEach {
            Log.d("NEW#",it.name+it.bucketId+it.coverUri)
        }
    }

    fun selectFolder(bucketId: String) {
        selectedFolder = folders.find { it.bucketId == bucketId }
    }
}