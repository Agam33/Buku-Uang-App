package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteKategori {
  suspend fun invoke(kategori: KategoriModel): Flow<ResourceState>
}