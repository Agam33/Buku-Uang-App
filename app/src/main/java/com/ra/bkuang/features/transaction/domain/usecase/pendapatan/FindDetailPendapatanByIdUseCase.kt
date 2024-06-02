package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import java.util.UUID

interface FindDetailPendapatanByIdUseCase {
  suspend fun invoke(id: UUID): DetailPendapatan
}