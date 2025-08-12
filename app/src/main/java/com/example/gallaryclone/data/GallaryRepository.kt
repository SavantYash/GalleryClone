package com.example.gallaryclone.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class GallaryRepository(private val context: Context) {

    fun getMedia(): List<MediaModel> {
        val mediaList = mutableListOf<MediaModel>()
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.BUCKET_ID,
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME
        )

        // Query Images
        queryMediaStore(
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection = projection,
            type = MediaType.IMAGE,
            extraProjection = null,
            mediaList = mediaList
        )

        // Query Videos
        queryMediaStore(
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection = projection,
            type = MediaType.VIDEO,
            extraProjection = arrayOf(MediaStore.Video.Media.DURATION),
            mediaList = mediaList
        )

        return mediaList.sortedByDescending { it.dateAdded }
    }

    private fun queryMediaStore(
        uri: Uri,
        projection: Array<String>,
        type: MediaType,
        extraProjection: Array<String>?,
        mediaList: MutableList<MediaModel>
    ) {
        val finalProjection = if (extraProjection != null) projection + extraProjection else projection
        context.contentResolver.query(uri, finalProjection, null, null,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC")?.use { cursor ->

            val idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
            val bucketIdCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_ID)
            val bucketNameCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
            val durationCol = if (type == MediaType.VIDEO) cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION) else -1

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val name = cursor.getString(nameCol)
                val date = cursor.getLong(dateCol)
                val bucketId = cursor.getString(bucketIdCol)
                val bucketName = cursor.getString(bucketNameCol)
                val duration = if (type == MediaType.VIDEO) cursor.getLong(durationCol) else null
                val uriItem = ContentUris.withAppendedId(uri, id)

                mediaList.add(
                    MediaModel(uriItem, name, date, type, bucketId, bucketName, duration)
                )
            }
        }
    }

    fun getFolders(): List<Folder> {
        val mediaList = getMedia()
        return mediaList
            .groupBy { it.bucketId }
            .map { (_, items) ->
                val sorted = items.sortedByDescending { it.dateAdded }
                Folder(
                    bucketId = sorted.first().bucketId,
                    name = sorted.first().bucketName,
                    coverUri = sorted.first().uri,
                    itemCount = sorted.size,
                    items = sorted
                )
            }
    }
}
