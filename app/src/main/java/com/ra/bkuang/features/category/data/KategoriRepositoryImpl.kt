package com.ra.bkuang.features.category.data

import com.ra.bkuang.features.category.data.local.KategoriLocalDataSource
import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class KategoriRepositoryImpl @Inject constructor(
  private val localDataSource: KategoriLocalDataSource
): KategoriRepository {
  override suspend fun findByType(type: TransactionType): Flow<List<KategoriModel>> {
    return localDataSource.findByType(type).map { i -> i.map { it.toModel() } }
  }

  override suspend fun save(kategori: KategoriModel) {
    return localDataSource.saveKategori(kategori.toEntity())
  }

  override suspend fun delete(kategori: KategoriModel) {
    return localDataSource.deleteKategori(kategori.toEntity())
  }

  override suspend fun update(kategori: KategoriModel) {
    return localDataSource.updateKategori(kategori.toEntity())
  }

  override fun findAll(): Flow<List<KategoriModel>> {
    return localDataSource.findAllKategori().map { i -> i.map { it.toModel() } }
  }

  override suspend fun findById(id: UUID): Flow<KategoriModel> {
    return localDataSource.findKategoriById(id).map { it.toModel() }
  }
}