package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.TabunganLocalDataSource
import com.ra.budgetplan.domain.mapper.AkunMapper
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AkunRepositoryImpl @Inject constructor(
  private val localDataSource: TabunganLocalDataSource
): AkunRepository {
  override suspend fun save(akunModel: AkunModel) {
    return localDataSource.save(AkunMapper.akunToEntity(akunModel))
  }

  override suspend fun delete(akunModel: AkunModel) {
    return localDataSource.delete(AkunMapper.akunToEntity(akunModel))
  }

  override suspend fun update(akunModel: AkunModel) {
    return localDataSource.update(AkunMapper.akunToEntity(akunModel))
  }

  override fun findAll(): Flow<List<AkunModel>> {
    return localDataSource.findAll().map { list ->
      list.map { AkunMapper.akunToModel(it) }
    }
  }

  override fun findById(id: UUID): Flow<AkunModel> {
    return localDataSource.findById(id).map {
      AkunMapper.akunToModel(it)
    }
  }
}