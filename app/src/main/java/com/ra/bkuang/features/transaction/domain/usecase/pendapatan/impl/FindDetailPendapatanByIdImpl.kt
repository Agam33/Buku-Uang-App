package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.FindDetailPendapatanById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindDetailPendapatanByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pendapatanRepository: PendapatanRepository
): FindDetailPendapatanById {

  override suspend fun invoke(id: UUID): DetailPendapatan = withContext(ioDispather) {
    return@withContext pendapatanRepository.findDetailById(id)
  }
}