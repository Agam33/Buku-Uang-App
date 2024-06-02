package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.domain.model.AkunModel
import java.util.UUID

interface FindAkunByIdUseCase {
  suspend operator fun invoke(id: UUID): AkunModel
}