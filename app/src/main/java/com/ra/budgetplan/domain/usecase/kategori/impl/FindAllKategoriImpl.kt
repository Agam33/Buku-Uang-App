package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.KategoriMapper
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.FindAllKategori
import com.ra.budgetplan.util.Resource
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
            KategoriMapper.kategoriToModel(category)
          }
          emit(Resource.Success(categories))
        }
      }
    }
  }
}