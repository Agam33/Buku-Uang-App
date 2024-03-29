package com.ra.bkuang.dummy.model

import com.ra.bkuang.data.local.database.entity.PembayaranHutangEntity
import java.time.LocalDateTime
import java.util.UUID

object PembayaranHutangDummy {
  private val listPembayaranHutang = listOf(
    PembayaranHutangEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    PembayaranHutangEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    PembayaranHutangEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
  )

  fun getAllPembayaranHutang(): List<PembayaranHutangEntity> = listPembayaranHutang
}