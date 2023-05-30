package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.FindKategoriById
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class FindKategoriByIdImpl @Inject constructor(
  private val repository: KategoriRepository
): FindKategoriById {

  override suspend fun invoke(id: UUID): Flow<KategoriModel> {
    return flow {
      repository.findById(id).collect {
        it.toModel()
      }
    }
  }
}