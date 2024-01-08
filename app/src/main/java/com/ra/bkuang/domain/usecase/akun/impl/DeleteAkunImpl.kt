package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.DeleteAkun
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAkunImpl @Inject constructor(
  private val akunRepository: AkunRepository
): DeleteAkun {
  override suspend fun invoke(akun: AkunModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      akunRepository.delete(akun.toEntity())
      emit(ResourceState.SUCCESS)
    }
  }
}