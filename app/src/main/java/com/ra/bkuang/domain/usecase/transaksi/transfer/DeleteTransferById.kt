package com.ra.bkuang.domain.usecase.transaksi.transfer

import com.ra.bkuang.util.ResourceState
import java.util.UUID

interface DeleteTransferById {
  suspend fun invoke(uuid: UUID): ResourceState
}