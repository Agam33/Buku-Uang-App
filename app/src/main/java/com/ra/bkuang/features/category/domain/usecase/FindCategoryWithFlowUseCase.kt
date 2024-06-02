package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface FindCategoryWithFlowUseCase {
  fun invoke(): Flow<HashMap<TransactionType, List<KategoriModel>>>
}