package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.features.transaction.domain.model.TransferModel

interface GetTransferByIdUseCase {
  suspend operator fun invoke(uuid: String): TransferModel
}