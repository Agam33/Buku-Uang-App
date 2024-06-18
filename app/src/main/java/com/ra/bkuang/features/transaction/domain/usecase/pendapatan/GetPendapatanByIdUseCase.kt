package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.domain.model.PendapatanModel

interface GetPendapatanByIdUseCase {
  suspend operator fun invoke(id: String): PendapatanModel?
}