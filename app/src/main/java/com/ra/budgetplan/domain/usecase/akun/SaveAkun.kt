package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel

interface SaveAkun {
  suspend fun invoke(akun: AkunModel)
}