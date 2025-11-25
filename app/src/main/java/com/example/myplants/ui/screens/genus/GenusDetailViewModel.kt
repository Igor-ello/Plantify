package com.example.myplants.ui.screens.genus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    private val genusRepository: GenusRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val genusId: Long = savedStateHandle.get<Long>("genusId") ?: 0L

    val genusLiveData: LiveData<Genus> = genusRepository.getGenusByIdLive(genusId)

    private val _editedGenus = MutableLiveData<Genus>()
    val editedGenus: LiveData<Genus> get() = _editedGenus

    init {
        // Сразу синхронизируемся с LiveData репозитория
        genusLiveData.observeForever { genus ->
            genus?.let {
                _editedGenus.value = it.copy()
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
        genusLiveData.value?.let {
            _editedGenus.value = it.copy()
        }
    }

    fun deleteGenus(onDeleted: () -> Unit) {
        val genus = _editedGenus.value ?: return
        viewModelScope.launch {
            genusRepository.deleteGenusById(genus.id)
            onDeleted()
        }
    }
}