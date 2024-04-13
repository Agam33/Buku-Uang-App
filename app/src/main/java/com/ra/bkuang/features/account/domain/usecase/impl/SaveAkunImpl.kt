package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.SaveAkun
import javax.inject.Inject

class SaveAkunImpl @Inject constructor(
  private val repository: AkunRepository
): SaveAkun {

  override suspend fun invoke(akun: AkunModel) {
    repository.save(akun)
  }
}