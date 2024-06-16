package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.features.transaction.domain.model.TransferModel
import java.util.UUID

interface GetTransferByIdUseCase {
  suspend operator fun invoke(uuid: UUID): TransferModel
}