package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.DeleteKategori
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteKategoriImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: KategoriRepository
): DeleteKategori {
  override suspend fun invoke(kategori: KategoriModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      repository.delete(kategori)
      emit(ResourceState.SUCCESS)
    }.flowOn(ioDispatcher)
  }
}