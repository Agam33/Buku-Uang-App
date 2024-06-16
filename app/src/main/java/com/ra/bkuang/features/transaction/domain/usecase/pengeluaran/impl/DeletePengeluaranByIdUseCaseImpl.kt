package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository,
): DeletePengeluaranByIdUseCase {
  override fun invoke(uuid: UUID): Flow<Result<Boolean>> {
    return repository.delete(uuid)
  }
}