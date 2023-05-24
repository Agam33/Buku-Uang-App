package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.KategoriMapper
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.SaveKategori
import javax.inject.Inject

class SaveKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): SaveKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.save(KategoriMapper.kategoriToEntity(kategori))
  }
}