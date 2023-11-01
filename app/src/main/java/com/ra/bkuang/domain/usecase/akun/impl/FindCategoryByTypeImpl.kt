package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.data.entity.KategoriEntity
import com.ra.bkuang.data.entity.TipeKategori
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class FindCategoryByTypeImpl @Inject constructor(
  private val repository: KategoriRepository
): FindCategoryByType {

  override fun invoke(type: TipeKategori): Flow<Resource<List<KategoriModel>>> {
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

  override fun invoke(): Flow<HashMap<TipeKategori, List<KategoriModel>>> {
    return flow {
      val expenseFlow = repository.findByType(TipeKategori.PENGELUARAN)
      val incomeFlow = repository.findByType(TipeKategori.PENDAPATAN)

      val zippedFlow: Flow<Pair<List<KategoriEntity>, List<KategoriEntity>>> =
        expenseFlow.zip(incomeFlow) { value1, value2 ->
          value1 to value2
      }

      zippedFlow.collect { (value1, value2) ->
        val result = HashMap<TipeKategori, List<KategoriModel>>()
        result[TipeKategori.PENDAPATAN] = value2.map { category ->
          category.toModel()
        }

        result[TipeKategori.PENGELUARAN] = value1.map { category ->
          category.toModel()
        }

        emit(result)
      }
    }
  }
}