package com.example.myplants.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.backup.BackupRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val backupRepository: BackupRepositoryInterface
) : ViewModel() {

    private val _backups = MutableStateFlow<List<File>>(emptyList())
    val backups: StateFlow<List<File>> get() = _backups

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> get() = _message

    init {
        refreshBackups()
    }

    fun refreshBackups() {
        _backups.value = backupRepository.listBackups()
    }

    fun backup() = viewModelScope.launch {
        backupRepository.createBackup()
        refreshBackups()
        _message.value = "Резервная копия создана"
    }

    fun restore(file: File) = viewModelScope.launch {
        backupRepository.restoreBackup(file)
        refreshBackups()
        _message.value = "Восстановлено из ${file.name}"
    }

    fun restoreMerge(file: File) = viewModelScope.launch {
        backupRepository.restoreMerge(file)
        refreshBackups()
        _message.value = "Восстановлено (merge) из ${file.name}"
    }

    fun deleteBackup(file: File) = viewModelScope.launch {
        backupRepository.deleteBackupFile(file)
        refreshBackups()
    }
}
