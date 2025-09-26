package com.example.myplants.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplants.data.backup.BackupRepositoryInterface
import kotlinx.coroutines.launch
import java.io.File

class SettingsViewModel(
    private val backupRepository: BackupRepositoryInterface
) : ViewModel() {

    private val _backups = MutableLiveData<List<File>>(emptyList())
    val backups: LiveData<List<File>> get() = _backups

    private val _message = MutableLiveData<String?>(null)
    val message: LiveData<String?> get() = _message

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
