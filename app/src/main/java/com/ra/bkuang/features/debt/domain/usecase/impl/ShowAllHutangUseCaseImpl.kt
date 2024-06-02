package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowAllHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): ShowAllHutangUseCase {
  override suspend fun invoke(): ResultState<List<HutangModel>>  {
    val list =  hutangRepository.findAll()
    if(list.isEmpty()) {
      return ResultState.Empty
    }
    return ResultState.Success(list)
  }
}