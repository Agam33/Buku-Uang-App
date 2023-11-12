package com.ra.bkuang.domain.usecase.budget

import com.ra.bkuang.data.local.entity.DetailBudget
import java.time.LocalDate

interface FindAllBudgetByDate {
  suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
}