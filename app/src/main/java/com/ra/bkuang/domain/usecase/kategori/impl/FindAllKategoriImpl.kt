package com.ra.bkuang.domain.usecase.kategori.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.FindAllKategori
import com.ra.bkuang.util.Resource
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
          val categories = list.map {category ->
            category.toModel()
          }
          emit(Resource.Success(categories))
        }
      }
    }
  }
}