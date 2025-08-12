package com.example.gallaryclone.data

import android.net.Uri

data class MediaModel(
    val uri: Uri,
    val displayName: String,
    val dateAdded: Long,
    val type: MediaType,
    val bucketId: String,
    val bucketName: String,
    val duration: Long? = null
)

enum class MediaType { IMAGE, VIDEO }

data class Folder(
    val bucketId: String,
    val name: String,
    val coverUri: Uri,
    val itemCount: Int,
    val items: List<MediaModel>
)