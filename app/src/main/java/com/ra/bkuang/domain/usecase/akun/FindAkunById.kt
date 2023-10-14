package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.domain.model.AkunModel
import java.util.UUID

interface FindAkunById {
  suspend fun invoke(id: UUID): AkunModel
}