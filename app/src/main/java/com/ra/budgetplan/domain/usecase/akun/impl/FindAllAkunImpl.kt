package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindAllAkunImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkun {
  override suspend fun invoke(): List<AkunModel> =
    repository.findAll().map { it.toModel() }
}