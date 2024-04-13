package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindAllKategori
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindAllKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): FindAllKategori {

  override fun invoke(): Flow<Resource<List<KategoriModel>>> {
    return flow {
      repository.findAll().collect { list ->
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
}