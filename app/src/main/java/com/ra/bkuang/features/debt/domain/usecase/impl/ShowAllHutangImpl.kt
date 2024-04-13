package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
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
  override suspend fun invoke(): Resource<List<HutangModel>> = withContext(ioDispatcher) {
    val list =  hutangRepository.findAll()
    if(list.isEmpty()) {
      return@withContext Resource.Empty("")
    }
    return@withContext Resource.Success(list)
  }
}