package com.ra.bkuang.features.category.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.data.local.KategoriLocalDataSource
import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class KategoriRepositoryImpl @Inject constructor(
  private val localDataSource: KategoriLocalDataSource
): KategoriRepository {
  override fun findByType(type: TransactionType): Flow<List<KategoriModel>> {
    return localDataSource.findByType(type).map { i -> i.map { it.toModel() } }
  }

  override fun save(kategori: KategoriModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.saveKategori(kategori.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
  }

  override fun delete(kategori: KategoriModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.deleteKategori(kategori.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
  }

  override fun update(kategori: KategoriModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.updateKategori(kategori.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
  }

  override fun findAll(): Flow<List<KategoriModel>> {
    return localDataSource.findAllKategori().map { i -> i.map { it.toModel() } }
  }

  override fun findById(id: UUID): Flow<KategoriModel> {
    return localDataSource.findKategoriById(id).map { it.toModel() }
  }
}