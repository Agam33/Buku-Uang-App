package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatanUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePendapatanUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository
): UpdatePendapatanUseCase {
  override fun invoke(newIncomeModel: PendapatanModel, oldIncomeModel: PendapatanModel): Flow<Result<Boolean>> {
    return repository.update(newIncomeModel, oldIncomeModel)
  }
}