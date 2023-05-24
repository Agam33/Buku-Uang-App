package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.KategoriLocalDataSource
import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.repository.KategoriRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class KategoriRepositoryImpl @Inject constructor(
  private val localDataSource: KategoriLocalDataSource
): KategoriRepository {
  override suspend fun findByType(type: TipeKategori): Flow<List<KategoriEntity>> {
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

  override suspend fun findById(id: UUID): KategoriEntity {
    return localDataSource.findKategoriById(id)
  }
}