package com.ra.bkuang.domain.usecase.transaksi.transfer

import com.ra.bkuang.domain.model.TransferModel
import com.ra.bkuang.domain.util.ResourceState

interface SaveTransfer {
  suspend fun invoke(transferModel: TransferModel): ResourceState
}