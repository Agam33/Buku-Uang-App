package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel

interface UpdateKategori {
  suspend fun invoke(kategori: KategoriModel)
}