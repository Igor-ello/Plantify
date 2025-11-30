package com.example.myplants.data.genus

import com.example.myplants.core.data.local.entity.Genus
import kotlinx.coroutines.flow.Flow

interface GenusRepositoryInterface {

    suspend fun insertGenus(genus: Genus): Long

    suspend fun insertGenera(genera: List<Genus>)

    suspend fun updateGenus(genus: Genus)

    suspend fun getGenusById(id: Long): Genus?

    fun getGenusByIdLive(id: Long): Flow<Genus?>

    suspend fun getGenusByName(genusName: String): Genus?

    fun getGenusByNameLive(genusName: String): Flow<Genus?>

    suspend fun getAllGenus(): List<Genus>

    fun getAllGenusLive(): Flow<List<Genus>>

    suspend fun deleteGenusById(id: Long)

    suspend fun deleteAllGenus()
}
