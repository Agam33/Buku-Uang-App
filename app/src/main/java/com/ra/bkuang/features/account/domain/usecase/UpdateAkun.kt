package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.domain.model.AkunModel

interface UpdateAkun {
  suspend fun invoke(akun: AkunModel)
}