package com.example.myplants.data.genus

import androidx.lifecycle.LiveData
import com.example.myplants.core.data.local.entity.Genus
import kotlinx.coroutines.flow.Flow

interface GenusRepositoryInterface {

    fun getGenusByIdLive(id: Long): LiveData<Genus>

    suspend fun getGenusById(id: Long): Genus

    fun getGenusByNameLive(genusName: String): LiveData<Genus>

    suspend fun getGenusByName(genusName: String): Genus

    suspend fun insertGenus(genus: Genus): Long

    suspend fun insertGenera(genera: List<Genus>)

    suspend fun updateGenus(genus: Genus)

    suspend fun deleteGenusById(id: Long)

    suspend fun deleteAllGenus()

    fun getAllGenus(): Flow<List<Genus>>
}
