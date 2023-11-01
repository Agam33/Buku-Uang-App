package com.ra.bkuang.domain.usecase.transaksi.transfer

import com.ra.bkuang.data.entity.DetailTransfer
import java.util.UUID

interface FindDetailTransferById {
  suspend fun invoke(id: UUID): DetailTransfer
}