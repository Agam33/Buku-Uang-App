package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface FindCategoryWithFlowUseCase {
  operator fun invoke(): Flow<HashMap<TransactionType, List<KategoriModel>>>
}