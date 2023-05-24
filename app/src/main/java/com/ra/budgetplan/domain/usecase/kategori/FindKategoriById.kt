package com.ra.budgetplan.domain.usecase.kategori

import com.ra.budgetplan.domain.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindKategoriById {
  suspend fun invoke(id: UUID): KategoriModel
}