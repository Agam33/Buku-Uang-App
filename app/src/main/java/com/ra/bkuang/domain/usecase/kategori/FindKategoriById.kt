package com.ra.bkuang.domain.usecase.kategori

import com.ra.bkuang.domain.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindKategoriById {
  suspend fun invoke(id: UUID): Flow<KategoriModel>
}