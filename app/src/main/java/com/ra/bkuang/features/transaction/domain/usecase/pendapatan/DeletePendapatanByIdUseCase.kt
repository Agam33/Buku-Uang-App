package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeletePendapatanByIdUseCase {
  fun invoke(uuid: UUID): Flow<Result<Boolean>>
}