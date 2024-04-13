package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import javax.inject.Inject

class FindAllAkunImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkun {
  override suspend fun invoke(): List<AkunModel> =
    repository.findAll()
}