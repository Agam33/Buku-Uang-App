package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.DeleteAkun
import com.ra.budgetplan.util.ResourceState
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