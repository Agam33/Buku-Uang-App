package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.FindKategoriById
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class FindKategoriByIdImpl @Inject constructor(
  private val repository: KategoriRepository
): FindKategoriById {

  override fun invoke(id: UUID): Flow<KategoriModel> {
    return repository.findById(id)
  }
}