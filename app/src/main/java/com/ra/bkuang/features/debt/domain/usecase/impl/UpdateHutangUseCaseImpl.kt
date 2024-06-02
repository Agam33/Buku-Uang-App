package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import javax.inject.Inject

class UpdateHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): UpdateHutangUseCase {
  override suspend fun invoke(hutangModel: HutangModel): Boolean {
   return hutangRepository.update(hutangModel)
  }
}