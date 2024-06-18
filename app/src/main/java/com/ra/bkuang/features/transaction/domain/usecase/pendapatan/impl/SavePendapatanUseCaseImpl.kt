package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.repository.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatanUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePendapatanUseCaseImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
): SavePendapatanUseCase {
  override fun invoke(incomeModel: PendapatanModel): Flow<Result<Boolean>> {
    return pendapatanRepository.save(incomeModel)
  }
}