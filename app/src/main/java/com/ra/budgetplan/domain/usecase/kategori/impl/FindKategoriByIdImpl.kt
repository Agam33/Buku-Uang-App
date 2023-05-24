package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.KategoriMapper
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

  override suspend fun invoke(id: UUID): KategoriModel {
    return KategoriMapper.kategoriToModel(repository.findById(id))
  }
}