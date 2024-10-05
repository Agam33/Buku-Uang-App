package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.data.model.AkunModel
import kotlinx.coroutines.flow.Flow

interface FindAllAkunWithFlowUseCase {
  operator fun invoke(): Flow<List<AkunModel>>

}