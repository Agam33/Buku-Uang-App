package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.DeleteAkun
import javax.inject.Inject

class DeleteAkunImpl @Inject constructor(
  private val repository: AkunRepository
): DeleteAkun {
  override suspend fun invoke(tabungan: AkunModel) {

  }
}