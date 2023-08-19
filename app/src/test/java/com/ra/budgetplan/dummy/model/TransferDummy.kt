package com.ra.budgetplan.dummy.model

import com.ra.budgetplan.domain.entity.TransferEntity
import java.time.LocalDateTime
import java.util.UUID

object TransferDummy {
  private val listTransfer = listOf(
    TransferEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-1",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    ),
    TransferEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-2",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    ),
    TransferEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-3",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    ),
  )

  fun getAllTransfer(): List<TransferEntity> = listTransfer
}