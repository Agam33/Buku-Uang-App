package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAkunById
import java.util.UUID
import javax.inject.Inject

class FindAkunByIdImpl @Inject constructor(
  private val repository: AkunRepository
): FindAkunById {

  override suspend fun invoke(id: UUID): AkunModel {
      return repository.findById(id)
  }
}