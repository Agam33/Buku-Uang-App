package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.DeleteAkun
import com.ra.bkuang.common.util.ResourceState
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