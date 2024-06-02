package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindCategoryByTypeUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): FindCategoryByTypeUseCase {

  override operator fun invoke(type: TransactionType): Flow<ResultState<List<KategoriModel>>> {
    return flow {
      repository.findByType(type).collect { list ->
        if(list.isEmpty()) {
          emit(ResultState.Empty)
        } else {
          val categories = list.map { category ->
            category
          }
          emit(ResultState.Success(categories))
        }
      }
    }
  }
}