package com.example.nestedlazycolumnjetpack.viewmodel
import com.example.nestedlazycolumnjetpack.model.ImageItem
import com.example.nestedlazycolumnjetpack.network.ApiClient
import com.example.nestedlazycolumnjetpack.network.ApiService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // StateFlow
    private val _images = MutableStateFlow<List<ImageItem>>(emptyList())
    val images: StateFlow<List<ImageItem>> = _images

    // fetch data dari API
    fun fetchImages() {
        viewModelScope.launch {
            try {
                // Panggil Retrofit API Service
                val response = ApiClient.apiService.getImages()
                // Update StateFlow dengan data baru
                _images.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                // Bisa tambahkan error handling di sini
            }
        }
    }
}
