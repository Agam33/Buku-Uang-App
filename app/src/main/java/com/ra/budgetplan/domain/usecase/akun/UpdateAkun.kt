package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel

interface UpdateAkun {
  suspend fun invoke(akun: AkunModel)
}