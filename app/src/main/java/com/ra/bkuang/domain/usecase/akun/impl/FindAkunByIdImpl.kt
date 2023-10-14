package com.ra.bkuang.domain.usecase.akun.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.FindAkunById
import java.util.UUID
import javax.inject.Inject

class FindAkunByIdImpl @Inject constructor(
  private val repository: AkunRepository
): FindAkunById {

  override suspend fun invoke(id: UUID): AkunModel {
      return repository.findById(id).toModel()
  }
}