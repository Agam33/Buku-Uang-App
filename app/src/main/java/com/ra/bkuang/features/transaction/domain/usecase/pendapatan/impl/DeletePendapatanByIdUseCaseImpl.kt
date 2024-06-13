package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class DeletePendapatanByIdUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository,
): DeletePendapatanByIdUseCase {

  override fun invoke(uuid: UUID): Flow<Result<Boolean>> {
    return repository.delete(uuid)
  }
}