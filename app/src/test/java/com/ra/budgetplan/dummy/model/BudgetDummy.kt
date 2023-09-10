package com.ra.budgetplan.dummy.model

import com.ra.budgetplan.domain.entity.BudgetEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

object BudgetDummy {

  private val listBudget = listOf(
    BudgetEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-1",
      10_000,
      100_000,
      LocalDate.parse("2023-10-20"),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    BudgetEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-2",
      10_000,
      100_000,
      LocalDate.parse("2023-10-21"),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
    BudgetEntity(
      UUID.randomUUID(),
      UUID.randomUUID(),
      "description-2",
      10_000,
      100_000,
      LocalDate.parse("2023-10-22"),
      LocalDateTime.now(),
      LocalDateTime.now()
    ),
  )

  fun getAllBudget(): List<BudgetEntity> {
    return listBudget
  }
}