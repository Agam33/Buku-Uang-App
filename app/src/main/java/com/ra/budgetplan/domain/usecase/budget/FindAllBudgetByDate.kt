package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.domain.model.BudgetModel
import java.time.LocalDate

interface FindAllBudgetByDate {
  suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
}