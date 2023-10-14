package com.ra.bkuang.domain.usecase.kategori.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.UpdateKategori
import javax.inject.Inject

class UpdateKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): UpdateKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.update(kategori.toEntity())
  }
}