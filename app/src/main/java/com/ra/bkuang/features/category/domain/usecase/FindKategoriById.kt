package com.ra.bkuang.features.category.domain.usecase

import com.ra.bkuang.features.category.domain.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindKategoriById {
  suspend fun invoke(id: UUID): Flow<KategoriModel>
}