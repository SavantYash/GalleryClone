package com.example.gallaryclone.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallaryclone.data.GallaryRepository

class ViewModelFactory(private val repository: GallaryRepository,private val context : Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GallaryViewModel(repository,context) as T
    }
}
