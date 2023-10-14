package com.ra.bkuang.domain.usecase.akun

import com.ra.bkuang.domain.model.AkunModel

interface FindAllAkun {
  suspend fun invoke(): List<AkunModel>
}