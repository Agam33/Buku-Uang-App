package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.domain.repository.KategoriRepository
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindCategoryByTypeUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): FindCategoryByTypeUseCase {

  override operator fun invoke(type: TransactionType): Flow<Result<List<KategoriModel>>> {
    return flow {
      repository.findByType(type).collect { list ->
        if(list.isEmpty()) {
          emit(Result.Error(""))
        } else {
          val categories = list.map { category ->
            category
          }
          emit(Result.Success(categories))
        }
      }
    }
  }
}