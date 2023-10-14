package com.ra.bkuang.domain.usecase.kategori

import com.ra.bkuang.domain.model.KategoriModel

interface SaveKategori {
  suspend fun invoke(kategori: KategoriModel)
}