package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.DeleteHutang
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): DeleteHutang {
  override suspend fun invoke(hutangModel: HutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      hutangRepository.delete(hutangModel.toEntity())
      emit(ResourceState.SUCCESS)
    }
  }
}