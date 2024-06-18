package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface FindCategoryByTypeUseCase {
  operator fun invoke(type: TransactionType): Flow<Result<List<KategoriModel>>>
}