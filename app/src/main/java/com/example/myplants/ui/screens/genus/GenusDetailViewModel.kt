package com.example.myplants.ui.screens.genus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.genus.GenusRepositoryInterface
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.core.data.local.entity.sections.CareInfo
import com.example.myplants.core.data.local.entity.sections.HealthInfo
import com.example.myplants.core.data.local.entity.sections.LifecycleInfo
import com.example.myplants.core.data.local.entity.sections.MainInfo
import com.example.myplants.core.data.local.entity.sections.StateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenusDetailViewModel @Inject constructor(
    private val repository: GenusRepositoryInterface
) : ViewModel() {

    private val _originalGenus = MutableLiveData<Genus?>()
    val originalGenus: LiveData<Genus?> = _originalGenus

    private val _editedGenus = MutableLiveData<Genus?>()
    val editedGenus: LiveData<Genus?> = _editedGenus

    init {
        loadGenus()
    }

    private fun loadGenus(genusId: Long? = null) {
        viewModelScope.launch {
            val genus = if (genusId != null) repository.getGenusById(genusId) else Genus.empty()
            _originalGenus.value = genus
            _editedGenus.value = genus?.copy()
        }
    }

    // Обновление текущего редактируемого рода
    fun updateEditedGenus(updatedGenus: Genus) {
        _editedGenus.value = updatedGenus
    }

    // Сброс изменений
    fun resetChanges() {
        _editedGenus.value = _originalGenus.value?.copy()
    }

    // Сохранение изменений
    fun saveChanges() {
        val genus = _editedGenus.value ?: return
        viewModelScope.launch {
            repository.updateGenus(genus)
            _originalGenus.value = genus.copy()
            _editedGenus.value = genus.copy()
        }
    }

    // Удаление рода
    fun deleteGenus(onDeleted: () -> Unit) {
        val genus = _editedGenus.value ?: return
        viewModelScope.launch {
            repository.deleteGenusById(genus.id)
            onDeleted()
        }
    }

    private fun Genus.Companion.empty() = Genus(
        id = 0L,
        main = MainInfo(
            genus = "",
            species = ""
        ),
        care = CareInfo(),
        lifecycle = LifecycleInfo(),
        health = HealthInfo(),
        state = StateInfo()
    )
}