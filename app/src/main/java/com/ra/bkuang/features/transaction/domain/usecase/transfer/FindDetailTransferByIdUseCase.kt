package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import java.util.UUID

interface FindDetailTransferByIdUseCase {
  suspend fun invoke(id: UUID): DetailTransfer
}