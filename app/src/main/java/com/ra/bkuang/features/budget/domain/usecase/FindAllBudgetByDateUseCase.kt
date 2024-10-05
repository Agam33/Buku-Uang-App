package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.core.data.source.local.database.entity.DetailBudget
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FindAllBudgetByDateUseCase {
  operator fun invoke(fromDate: LocalDate, toDate: LocalDate): Flow<List<DetailBudget>>
}