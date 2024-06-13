package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaranUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePengeluaranUseCaseImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository,
): SavePengeluaranUseCase {
  override fun invoke(pengeluaranModel: PengeluaranModel): Flow<Result<Boolean>> {
    return pengeluaranRepository.save(pengeluaranModel)
  }
}