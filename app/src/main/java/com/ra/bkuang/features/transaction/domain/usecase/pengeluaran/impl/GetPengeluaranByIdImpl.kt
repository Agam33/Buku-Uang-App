package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class GetPengeluaranByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PengeluaranRepository
): GetPengeluaranById {
  override suspend fun invoke(uuid: UUID): PengeluaranModel = withContext(ioDispather) {
    return@withContext repository.findById(uuid)
  }
}