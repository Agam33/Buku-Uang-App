package com.ra.bkuang.features.transaction.domain

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface TransferRepository {
  suspend fun findById(uuid: UUID): TransferModel
  suspend fun findDetailById(uuid: UUID): DetailTransfer
  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailTransfer>>>
  fun save(transfer: TransferModel): Flow<Result<Boolean>>
  fun delete(uuid: UUID): Flow<Result<Boolean>>
  fun update(newTransferModel: TransferModel, oldTransferModel: TransferModel): Flow<Result<Boolean>>
}