package com.ra.bkuang.dummy.model

import com.ra.bkuang.data.entity.HutangEntity
import java.time.LocalDateTime
import java.util.UUID

object HutangDummy {
  private val listHutang = listOf(
    HutangEntity(
      UUID.randomUUID(),
      "hutang-1",
      "description-1",
      10_000,
      100_000,
      101,
      false,
      "10-20-2023",
      LocalDateTime.now(),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    HutangEntity(
      UUID.randomUUID(),
      "hutang-2",
      "description-2",
      10_000,
      100_000,
      102,
      false,
      "10-20-2023",
      LocalDateTime.now(),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    HutangEntity(
      UUID.randomUUID(),
      "hutang-2",
      "description-2",
      10_000,
      100_000,
      103,
      false,
      "10-20-2023",
      LocalDateTime.now(),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
  )

  fun getAllHutang(): List<HutangEntity> {
    return listHutang
  }
}