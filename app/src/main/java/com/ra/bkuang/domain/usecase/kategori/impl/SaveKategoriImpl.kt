package com.ra.bkuang.domain.usecase.kategori.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.SaveKategori
import javax.inject.Inject

class SaveKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): SaveKategori {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.save(kategori.toEntity())
  }
}