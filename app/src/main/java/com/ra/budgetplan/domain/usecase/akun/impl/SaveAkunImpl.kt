package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.SaveAkun
import javax.inject.Inject

class SaveAkunImpl @Inject constructor(
  private val repository: AkunRepository
): SaveAkun {

  override suspend fun invoke(akun: AkunModel) {
    repository.save(akun.toEntity())
  }
}