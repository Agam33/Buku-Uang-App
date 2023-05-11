package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel

interface DeleteKategori {
  suspend fun invoke(kategori: KategoriModel)
}