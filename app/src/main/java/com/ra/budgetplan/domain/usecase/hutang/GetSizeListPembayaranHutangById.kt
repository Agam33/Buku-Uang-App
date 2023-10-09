package com.ra.budgetplan.domain.usecase.hutang

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GetSizeListPembayaranHutangById {
  fun invoke(id: UUID): Flow<Int?>
}