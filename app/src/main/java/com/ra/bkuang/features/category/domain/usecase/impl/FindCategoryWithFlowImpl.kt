package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindCategoryWithFlow
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class FindCategoryWithFlowImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: KategoriRepository
): FindCategoryWithFlow {

  override fun invoke(): Flow<HashMap<TransactionType, List<KategoriModel>>> {
    return flow {
      val expenseFlow = repository.findByType(TransactionType.PENGELUARAN)
      val incomeFlow = repository.findByType(TransactionType.PENDAPATAN)

      val zippedFlow: Flow<Pair<List<KategoriModel>, List<KategoriModel>>> =
        expenseFlow.zip(incomeFlow) { value1, value2 ->
          value1 to value2
        }

      zippedFlow.collect { (value1, value2) ->
        val result = HashMap<TransactionType, List<KategoriModel>>()
        result[TransactionType.PENDAPATAN] = value2

        result[TransactionType.PENGELUARAN] = value1

        emit(result)
      }
    }.flowOn(ioDispatcher)
  }
}