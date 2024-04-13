package com.ra.bkuang.features.category.data

import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.category.data.local.KategoriLocalDataSource
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class KategoriRepositoryImpl @Inject constructor(
  private val localDataSource: KategoriLocalDataSource
): KategoriRepository {
  override suspend fun findByType(type: TransactionType): Flow<List<KategoriEntity>> {
    return localDataSource.findByType(type)
  }

  override suspend fun save(kategori: KategoriEntity) {
    return localDataSource.saveKategori(kategori)
  }

  override suspend fun delete(kategori: KategoriEntity) {
    return localDataSource.deleteKategori(kategori)
  }

  override suspend fun update(kategori: KategoriEntity) {
    return localDataSource.updateKategori(kategori)
  }

  override fun findAll(): Flow<List<KategoriEntity>> {
    return localDataSource.findAllKategori()
  }

  override suspend fun findById(id: UUID): Flow<KategoriEntity> {
    return localDataSource.findKategoriById(id)
  }
}