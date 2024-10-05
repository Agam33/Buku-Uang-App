package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface FindCategoryByTypeUseCase {
  operator fun invoke(type: TransactionType): Flow<Result<List<KategoriModel>>>
}