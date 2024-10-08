package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.dao.KategoriDao
import com.ra.bkuang.core.data.source.local.database.entity.KategoriEntity
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class KategoriLocalDataSourceImpl @Inject constructor(
  private val kategoriDao: KategoriDao
): KategoriLocalDataSource {
  override fun findByType(type: TransactionType): Flow<List<KategoriEntity>> {
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

  override fun findKategoriById(id: UUID): Flow<KategoriEntity> {
    return kategoriDao.findById(id)
  }
}