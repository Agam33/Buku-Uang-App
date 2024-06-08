package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowAllHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): ShowAllHutangUseCase {
  override operator fun invoke(): Flow<Result<List<HutangModel>>> {
    return hutangRepository.findAllWithFlow()
  }
}