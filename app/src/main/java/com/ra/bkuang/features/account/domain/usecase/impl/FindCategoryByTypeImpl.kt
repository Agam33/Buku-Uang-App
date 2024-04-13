package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class FindCategoryByTypeImpl @Inject constructor(
  private val repository: KategoriRepository
): FindCategoryByType {

  override fun invoke(type: TransactionType): Flow<Resource<List<KategoriModel>>> {
    return flow {
      repository.findByType(type).collect { list ->
        if(list.isEmpty()) {
          emit(Resource.Empty(""))
        } else {
          val categories = list.map { category ->
            category.toModel()
          }
          emit(Resource.Success(categories))
        }
      }
    }
  }

  override fun invoke(): Flow<HashMap<TransactionType, List<KategoriModel>>> {
    return flow {
      val expenseFlow = repository.findByType(TransactionType.PENGELUARAN)
      val incomeFlow = repository.findByType(TransactionType.PENDAPATAN)

      val zippedFlow: Flow<Pair<List<KategoriEntity>, List<KategoriEntity>>> =
        expenseFlow.zip(incomeFlow) { value1, value2 ->
          value1 to value2
      }

      zippedFlow.collect { (value1, value2) ->
        val result = HashMap<TransactionType, List<KategoriModel>>()
        result[TransactionType.PENDAPATAN] = value2.map { category ->
          category.toModel()
        }

        result[TransactionType.PENGELUARAN] = value1.map { category ->
          category.toModel()
        }

        emit(result)
      }
    }
  }
}