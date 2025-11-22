package com.example.myplants.data.genus

import com.example.myplants.core.data.local.db.dao.GenusDao
import com.example.myplants.core.data.local.entity.Genus

class GenusRepository(
    private val genusDao: GenusDao
) : GenusRepositoryInterface {

    override fun getGenusByIdLive(id: Long) =
        genusDao.getGenusByIdLive(id)

    override suspend fun getGenusById(id: Long) =
        genusDao.getGenusById(id)

    override fun getGenusByNameLive(genusName: String) =
        genusDao.getGenusByNameLive(genusName)

    override suspend fun getGenusByName(genusName: String) =
        genusDao.getGenusByName(genusName)

    override suspend fun insertGenus(genus: Genus) =
        genusDao.insertGenus(genus)

    override suspend fun insertGenera(genera: List<Genus>) =
        genusDao.insertGenera(genera)

    override suspend fun updateGenus(genus: Genus) =
        genusDao.updateGenus(genus)

    override suspend fun deleteGenusById(id: Long) =
        genusDao.deleteGenusById(id)

    override suspend fun deleteAllGenus() =
        genusDao.deleteAllGenus()

    override suspend fun getAllGenus() =
        genusDao.getAllGenus()
}