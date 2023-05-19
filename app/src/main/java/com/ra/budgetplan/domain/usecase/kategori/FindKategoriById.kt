package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindKategoriById {
  fun invoke(id: UUID): Flow<KategoriModel>
}