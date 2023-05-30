package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.UpdateKategori
import javax.inject.Inject

class UpdateKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): UpdateKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.update(kategori.toEntity())
  }
}