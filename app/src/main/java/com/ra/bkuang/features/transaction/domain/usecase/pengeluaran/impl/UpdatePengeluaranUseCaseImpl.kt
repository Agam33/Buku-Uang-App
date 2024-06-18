package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.repository.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaranUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePengeluaranUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository,
): UpdatePengeluaranUseCase {
  override fun invoke(
    newPengeluaranModel: PengeluaranModel,
    oldPengeluaranModel: PengeluaranModel
  ): Flow<Result<Boolean>> {
    return repository.update(newPengeluaranModel, oldPengeluaranModel)
 }
}