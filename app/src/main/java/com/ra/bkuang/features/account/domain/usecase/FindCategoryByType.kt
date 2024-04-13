package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface FindCategoryByType {
  fun invoke(type: TransactionType): Flow<Resource<List<KategoriModel>>>
}