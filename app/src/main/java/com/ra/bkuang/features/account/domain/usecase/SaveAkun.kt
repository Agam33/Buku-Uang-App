package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.domain.model.AkunModel

interface SaveAkun {
  suspend fun invoke(akun: AkunModel)
}