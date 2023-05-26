package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.UpdateAkun
import javax.inject.Inject

class UpdateAkunImpl @Inject constructor(
  private val repository: AkunRepository
): UpdateAkun {
  override suspend fun invoke(akun: AkunModel) {
    repository.update(akun.toEntity())
  }
}