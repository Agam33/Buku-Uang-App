package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.CicilanLocalDataSource
import com.ra.budgetplan.domain.mapper.CicilanMapper
import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CicilanRepositoryImpl @Inject constructor(
  private val localDataSource: CicilanLocalDataSource
): CicilanRepository {
  override suspend fun save(cicilan: CicilanModel) {
    return localDataSource.save(CicilanMapper.hutangToEntity(cicilan))
  }

  override suspend fun delete(cicilan: CicilanModel) {
    return localDataSource.delete(CicilanMapper.hutangToEntity(cicilan))
  }

  override suspend fun update(cicilan: CicilanModel) {
    return localDataSource.update(CicilanMapper.hutangToEntity(cicilan))
  }

  override fun findById(id: UUID): Flow<CicilanModel> {
    return localDataSource.findById(id).map {
      CicilanMapper.hutangToModel(it)
    }
  }

  override fun findAll(): Flow<List<CicilanModel>> {
    return localDataSource.findAll().map { list->
      list.map { CicilanMapper.hutangToModel(it) }
    }
  }
}