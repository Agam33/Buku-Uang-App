package com.ra.bkuang.dummy.model

import com.ra.bkuang.data.local.database.entity.PendapatanEntity
import java.time.LocalDateTime
import java.util.UUID

object PendapatanDummy {
  private val listPendapatan = listOf(
    PendapatanEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-1",
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    PendapatanEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-2",
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    PendapatanEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-3",
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
  )

  fun getAllPendapatan(): List<PendapatanEntity> = listPendapatan

  fun getTotalPendapatan(): Long {
    var total = 0L

    for(pendapatan in listPendapatan) {
      total += pendapatan.jumlah
    }

    return total
  }
}