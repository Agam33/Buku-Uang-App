package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.SaveKategori
import javax.inject.Inject

class SaveKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): SaveKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.save(kategori.toEntity())
  }
}