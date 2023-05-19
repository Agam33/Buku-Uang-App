package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import javax.inject.Inject

class DeleteKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): DeleteKategori {
  override suspend fun invoke(kategori: KategoriModel) {

  }
}