package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.domain.repository.KategoriRepository
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindCategoryWithFlowUseCase
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class FindCategoryWithFlowUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): FindCategoryWithFlowUseCase {

  override operator fun invoke(): Flow<HashMap<TransactionType, List<KategoriModel>>> {
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
    }
  }
}