package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class FindCategoryByTypeImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: KategoriRepository
): FindCategoryByType {

  override fun invoke(type: TransactionType): Flow<Resource<List<KategoriModel>>> {
    return flow<Resource<List<KategoriModel>>> {
      repository.findByType(type).collect { list ->
        if(list.isEmpty()) {
          emit(Resource.Empty(""))
        } else {
          val categories = list.map { category ->
            category
          }
          emit(Resource.Success(categories))
        }
      }
    }.flowOn(ioDispatcher)
  }
}