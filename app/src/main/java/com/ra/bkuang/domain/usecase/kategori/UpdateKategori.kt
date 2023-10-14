package com.ra.bkuang.domain.usecase.kategori

import com.ra.bkuang.domain.model.KategoriModel

interface UpdateKategori {
  suspend fun invoke(kategori: KategoriModel)
}