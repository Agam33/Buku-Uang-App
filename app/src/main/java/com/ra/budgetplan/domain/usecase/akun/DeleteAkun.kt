package com.ra.budgetplan.domain.usecase.akun

import com.ra.budgetplan.domain.model.AkunModel

interface DeleteAkun {
  suspend fun invoke(tabungan: AkunModel)
}