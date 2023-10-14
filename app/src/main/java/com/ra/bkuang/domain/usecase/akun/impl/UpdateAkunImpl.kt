package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.UpdateAkun
import javax.inject.Inject

class UpdateAkunImpl @Inject constructor(
  private val repository: AkunRepository
): UpdateAkun {
  override suspend fun invoke(akun: AkunModel) {
    repository.update(akun.toEntity())
  }
}