package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
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