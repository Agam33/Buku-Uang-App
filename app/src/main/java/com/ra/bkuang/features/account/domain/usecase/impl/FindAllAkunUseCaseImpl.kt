package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import javax.inject.Inject

class FindAllAkunUseCaseImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkunUseCase {
  override operator fun invoke(): List<AkunModel> {
    return repository.findAll()
  }
}