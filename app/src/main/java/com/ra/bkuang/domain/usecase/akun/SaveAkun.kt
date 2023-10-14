package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.domain.model.AkunModel

interface SaveAkun {
  suspend fun invoke(akun: AkunModel)
}