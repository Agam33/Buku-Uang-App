package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindHutangByIdWithFlow {
  fun invoke(id: UUID): Flow<HutangModel>
}