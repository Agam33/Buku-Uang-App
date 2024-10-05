package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAkunByIdUseCase
import java.util.UUID
import javax.inject.Inject

class FindAkunByIdUseCaseImpl @Inject constructor(
  private val repository: AkunRepository
): FindAkunByIdUseCase {

  override suspend operator fun invoke(id: UUID): AkunModel {
      return repository.findById(id)
  }
}