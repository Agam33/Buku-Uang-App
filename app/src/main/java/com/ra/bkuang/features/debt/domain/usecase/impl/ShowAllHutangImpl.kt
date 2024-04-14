package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowAllHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): ShowAllHutang {
  override suspend fun invoke(): ResultState<List<HutangModel>> = withContext(ioDispatcher) {
    val list =  hutangRepository.findAll()
    if(list.isEmpty()) {
      return@withContext ResultState.Empty
    }
    return@withContext ResultState.Success(list)
  }
}