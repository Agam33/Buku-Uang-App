package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.analytics.domain.repository.AnalyticsRepository
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalyticsUseCase
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class DetailAnalyticsUseCaseImpl @Inject constructor(
  private val analyticsRepository: AnalyticsRepository
): DetailAnalyticsUseCase {

  override operator fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Result<List<TransactionDetail>>> {
    return analyticsRepository.detailAnalytics(transactionType, fromDate, toDate)
  }
}