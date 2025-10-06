package com.example.myplants.ui.viewmodels

import com.example.myplants.dao.GenusDao
import com.example.myplants.models.Genus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenusRepository(
    private val genusDao: GenusDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getGenusById(id: Long): Genus? = withContext(dispatcher) {
        genusDao.getGenusById(id)
    }

    suspend fun insertGenus(genus: Genus): Long = withContext(dispatcher) {
        genusDao.insertGenus(genus)
    }

    suspend fun updateGenus(genus: Genus) = withContext(dispatcher) {
        genusDao.updateGenus(genus)
    }

    suspend fun deleteGenus(genus: Genus) = withContext(dispatcher) {
        genusDao.deleteGenus(genus)
    }
}
