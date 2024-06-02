package com.ra.bkuang.features.debt.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetSizeListPembayaranHutangByIdUseCase {
  fun invoke(id: String): Flow<Int?>
}