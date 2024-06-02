package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.SaveKategoriUseCase
import javax.inject.Inject

class SaveKategoriUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): SaveKategoriUseCase {
  override suspend fun invoke(kategori: KategoriModel) {
    repository.save(kategori)
  }
}