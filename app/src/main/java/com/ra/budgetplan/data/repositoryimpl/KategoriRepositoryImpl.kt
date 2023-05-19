package com.ra.budgetplan.data.repositoryimpl

import androidx.lifecycle.map
import com.ra.budgetplan.data.local.KategoriLocalDataSource
import com.ra.budgetplan.domain.mapper.KategoriMapper
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class KategoriRepositoryImpl @Inject constructor(
  private val localDataSource: KategoriLocalDataSource
): KategoriRepository {
  override suspend fun save(kategori: KategoriModel) {
    return localDataSource.saveKategori(KategoriMapper.kategoriToEntity(kategori))
  }

  override suspend fun delete(kategori: KategoriModel) {
    return localDataSource.deleteKategori(KategoriMapper.kategoriToEntity(kategori))
  }

  override suspend fun update(kategori: KategoriModel) {
    return localDataSource.updateKategori(KategoriMapper.kategoriToEntity(kategori))
  }

  override fun findAll(): Flow<List<KategoriModel>> {
    return localDataSource.findAllKategori().map { list ->
      list.map { kategori ->
        KategoriMapper.kategoriToModel(kategori)
      }
    }
  }

  override fun findById(id: UUID): Flow<KategoriModel> {
    return flow {
      localDataSource.findKategoriById(id).map {
        KategoriMapper.kategoriToModel(it)
      }
    }
  }
}