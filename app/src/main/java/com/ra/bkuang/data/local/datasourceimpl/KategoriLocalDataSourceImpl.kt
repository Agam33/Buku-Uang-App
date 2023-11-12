package com.ra.bkuang.data.local.datasourceimpl

import com.ra.bkuang.data.local.KategoriLocalDataSource
import com.ra.bkuang.data.local.database.dao.KategoriDao
import com.ra.bkuang.data.local.entity.KategoriEntity
import com.ra.bkuang.data.local.entity.TipeKategori
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class KategoriLocalDataSourceImpl @Inject constructor(
  private val kategoriDao: KategoriDao
): KategoriLocalDataSource {
  override suspend fun findByType(type: TipeKategori): Flow<List<KategoriEntity>> {
    return kategoriDao.findByType(type)
  }

  override suspend fun saveKategori(kategori: KategoriEntity) {
    return kategoriDao.save(kategori)
  }

  override suspend fun deleteKategori(kategori: KategoriEntity) {
    return kategoriDao.delete(kategori)
  }

  override suspend fun updateKategori(kategori: KategoriEntity) {
    return kategoriDao.update(kategori)
  }

  override fun findAllKategori(): Flow<List<KategoriEntity>> {
    return kategoriDao.findAll()
  }

  override suspend fun findKategoriById(id: UUID): Flow<KategoriEntity> {
    return kategoriDao.findById(id)
  }
}