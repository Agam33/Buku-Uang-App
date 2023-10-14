package com.ra.bkuang.domain.usecase.transaksi.transfer

import com.ra.bkuang.domain.model.TransferModel
import java.util.UUID

interface GetTransferById {
  suspend fun invoke(uuid: UUID): TransferModel
}