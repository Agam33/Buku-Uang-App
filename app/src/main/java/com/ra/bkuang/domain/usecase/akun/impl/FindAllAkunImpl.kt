package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import javax.inject.Inject

class FindAllAkunImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkun {
  override suspend fun invoke(): List<AkunModel> =
    repository.findAll().map { it.toModel() }
}