package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.common.util.ResourceState

interface SaveTransfer {
  suspend fun invoke(transferModel: TransferModel): ResourceState
}