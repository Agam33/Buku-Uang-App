package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.domain.repository.KategoriRepository
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.UpdateKategoriUseCase
import javax.inject.Inject

class UpdateKategoriUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): UpdateKategoriUseCase {
  override operator fun invoke(kategori: KategoriModel) =
    repository.update(kategori)
}