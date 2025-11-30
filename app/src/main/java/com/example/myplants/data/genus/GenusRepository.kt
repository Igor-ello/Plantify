package com.example.myplants.data.genus

import com.example.myplants.core.data.local.db.dao.GenusDao
import com.example.myplants.core.data.local.entity.Genus
import kotlinx.coroutines.flow.Flow

class GenusRepository(
    private val genusDao: GenusDao
) : GenusRepositoryInterface {

    override suspend fun insertGenus(genus: Genus): Long =
        genusDao.insertGenus(genus)

    override suspend fun insertGenera(genera: List<Genus>) =
        genusDao.insertGenera(genera)

    override suspend fun updateGenus(genus: Genus) =
        genusDao.updateGenus(genus)

    override suspend fun getGenusById(id: Long): Genus? =
        genusDao.getGenusById(id)

    override fun getGenusByIdLive(id: Long): Flow<Genus?> =
        genusDao.getGenusByIdLive(id)

    override suspend fun getGenusByName(genusName: String): Genus? =
        genusDao.getGenusByName(genusName)

    override fun getGenusByNameLive(genusName: String): Flow<Genus?> =
        genusDao.getGenusByNameLive(genusName)

    override suspend fun getAllGenus(): List<Genus> =
        genusDao.getAllGenus()

    override fun getAllGenusLive(): Flow<List<Genus>> =
        genusDao.getAllGenusLive()

    override suspend fun deleteGenusById(id: Long) =
        genusDao.deleteGenusById(id)

    override suspend fun deleteAllGenus() =
        genusDao.deleteAllGenus()
}