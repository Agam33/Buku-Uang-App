package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.UpdateAkun
import javax.inject.Inject

class UpdateAkunImpl @Inject constructor(
  private val repository: AkunRepository
): UpdateAkun {
  override suspend fun invoke(akun: AkunModel) {
    repository.update(akun.toEntity())
  }
}