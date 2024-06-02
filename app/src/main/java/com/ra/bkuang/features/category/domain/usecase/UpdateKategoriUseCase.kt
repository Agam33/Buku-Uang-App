package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel

interface UpdateKategoriUseCase {
  suspend operator fun invoke(kategori: KategoriModel)
}