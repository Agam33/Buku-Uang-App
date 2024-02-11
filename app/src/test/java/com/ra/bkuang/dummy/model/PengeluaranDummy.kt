package com.ra.bkuang.dummy.model

import com.ra.bkuang.data.local.entity.PengeluaranEntity
import java.time.LocalDateTime
import java.util.UUID

object PengeluaranDummy {
  private val listPengeluaran = listOf(
    PengeluaranEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-1",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    ),
    PengeluaranEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-2",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    ),
    PengeluaranEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-3",
      10_000,
      LocalDateTime.now(),
      LocalDateTime.now(),
    )
  )

  fun getAllPengeluaran(): List<PengeluaranEntity> = listPengeluaran

  fun getTotalPengeluaran(): Long {
    var total = 0L
    for(item in listPengeluaran) {
      total += item.jumlah
    }
    return total
  }
}