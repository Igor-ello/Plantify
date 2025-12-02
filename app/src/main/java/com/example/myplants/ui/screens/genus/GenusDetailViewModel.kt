package com.example.myplants.ui.screens.genus

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.core.data.local.entity.Genus
import com.example.myplants.data.genus.GenusRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenusDetailViewModel @Inject constructor(
    private val genusRepository: GenusRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val genusId: Long = savedStateHandle.get<Long>("genusId") ?: 0L

    private val genus: Flow<Genus?> = genusRepository.getGenusByIdLive(genusId)

    private val _editedGenus = MutableStateFlow<Genus?>(null)
    val editedGenus: StateFlow<Genus?> = _editedGenus.asStateFlow()

    init {
        viewModelScope.launch {
            genus.collect { genus ->
                genus?.let {
                    _editedGenus.value = it.copy()
                }
            }
        }
    }

    fun updateEditedGenus(genus: Genus) {
        _editedGenus.value = genus
    }

    fun saveChanges() {
        val genus = _editedGenus.value ?: return
        viewModelScope.launch {
            genusRepository.updateGenus(genus)
        }
    }

    fun resetChanges() {
        viewModelScope.launch {
            genus.collect { genus ->
                genus?.let {
                    _editedGenus.value = it.copy()
                }
            }
        }
    }

    fun deleteGenus(onDeleted: () -> Unit) {
        viewModelScope.launch {
            genusRepository.deleteGenusById(genusId)
            onDeleted()
        }
    }
}