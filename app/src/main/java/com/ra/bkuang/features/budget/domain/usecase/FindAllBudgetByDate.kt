package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.features.budget.data.local.DetailBudget
import java.time.LocalDate

interface FindAllBudgetByDate {
  suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
}