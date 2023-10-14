package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.SaveAkun
import javax.inject.Inject

class SaveAkunImpl @Inject constructor(
  private val repository: AkunRepository
): SaveAkun {

  override suspend fun invoke(akun: AkunModel) {
    repository.save(akun.toEntity())
  }
}