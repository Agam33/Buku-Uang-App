package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): DeleteHutang {
  override suspend fun invoke(hutangModel: HutangModel): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)
      hutangRepository.delete(hutangModel.toEntity())
      emit(StatusItem.SUCCESS)
    }
  }
}