package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow

interface DeleteKategori {
  suspend fun invoke(kategori: KategoriModel): Flow<StatusItem>
}