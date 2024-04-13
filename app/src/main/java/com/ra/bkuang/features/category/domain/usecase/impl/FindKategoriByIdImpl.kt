package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindKategoriById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class FindKategoriByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: KategoriRepository
): FindKategoriById {

  override suspend fun invoke(id: UUID): Flow<KategoriModel> {
    return flow {
      repository.findById(id).collect {
        emit(it)
      }
    }.flowOn(ioDispatcher)
  }
}