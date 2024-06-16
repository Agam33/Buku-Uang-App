package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.analytics.domain.AnalyticsRepository
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticListUseCase
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class ShowAnalyticListUseCaseImpl @Inject constructor(
  private val analyticsRepository: AnalyticsRepository
): ShowAnalyticListUseCase {

  override operator fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Result<List<AnalyticModel>>> {
    return analyticsRepository.showAnalyticList(transactionType, fromDate, toDate)
  }
}