package com.ra.bkuang.features.analytics.presentation

import com.ra.bkuang.features.analytics.data.model.AnalyticModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail

data class AnalyticUiState(
    val analytics: List<AnalyticModel> = emptyList(),
    val detailAnalyticList: List<TransactionDetail> = emptyList(),
)
