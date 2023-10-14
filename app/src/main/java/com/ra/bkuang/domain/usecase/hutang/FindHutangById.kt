package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import java.util.UUID

interface FindHutangById {
  suspend fun invoke(id: UUID): HutangModel
}