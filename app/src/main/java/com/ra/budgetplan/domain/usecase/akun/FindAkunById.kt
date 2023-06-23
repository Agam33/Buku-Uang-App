package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindAkunById {
  suspend fun invoke(id: UUID): AkunModel
}