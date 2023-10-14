package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.UpdateHutang
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): UpdateHutang {
  override fun invoke(hutangModel: HutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      hutangRepository.update(hutangModel.toEntity())
      emit(ResourceState.SUCCESS)
    }
  }
}