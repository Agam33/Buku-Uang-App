package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeletePengeluaranByIdUseCase {
  operator fun invoke(uuid: UUID): Flow<Result<Boolean>>
}