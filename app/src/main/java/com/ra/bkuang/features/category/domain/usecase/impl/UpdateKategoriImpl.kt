package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.UpdateKategori
import javax.inject.Inject

class UpdateKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): UpdateKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.update(kategori.toEntity())
  }
}