package com.ra.bkuang.dummy.model

import com.ra.bkuang.domain.entity.AkunEntity
import java.time.LocalDateTime
import java.util.UUID

object AkunDummy {

  private val accountList = listOf(
    AkunEntity(
      UUID.randomUUID(),
      "",
      -1,
      "CASH-1",
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),

    AkunEntity(
      UUID.randomUUID(),
      "",
      -1,
      "CASH-2",
      200_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    ),

    AkunEntity(
      UUID.randomUUID(),
      "",
      -1,
      "CASH-3",
      300_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    )
  )

  fun getAllAccounts(): List<AkunEntity> {
    return accountList;
  }

  fun getTotalMoney(): Long {
    var totalMoney = 0L
    for(akun in accountList) {
      totalMoney += akun.total
    }
    return totalMoney
  }
}