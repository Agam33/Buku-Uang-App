package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.domain.model.AkunModel

interface UpdateAkun {
  suspend fun invoke(akun: AkunModel)
}