package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.common.util.ResourceState
import java.util.UUID

interface DeleteTransferById {
  suspend fun invoke(uuid: UUID): ResourceState
}