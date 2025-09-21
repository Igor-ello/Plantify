package com.example.myplants.plants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myplants.models.PlantPhoto


class PlantPhotosViewModel : ViewModel() {

    private val _photos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val photos: LiveData<List<PlantPhoto>> = _photos

    fun setPhotos(newPhotos: List<PlantPhoto>) {
        _photos.value = newPhotos
    }

    fun addPhoto(uri: String, isPrimary: Boolean = false) {
        val current = _photos.value ?: emptyList()
        val updated = if (isPrimary) {
            current.map { it.copy(isPrimary = false) } + PlantPhoto(uri = uri, isPrimary = true, plantId = 0)
        } else current + PlantPhoto(uri = uri, plantId = 0)
        _photos.value = updated
    }

    fun removePhoto(photoId: Long) {
        _photos.value = _photos.value?.filter { it.id != photoId }
    }

    fun setPrimaryPhoto(photoId: Long) {
        _photos.value = _photos.value?.map {
            it.copy(isPrimary = it.id == photoId)
        }
    }

    fun clear() {
        _photos.value = emptyList()
    }
}
