package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.FindDetailPengeluaranById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindDetailPengeluaranByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pengeluaranRepository: PengeluaranRepository
): FindDetailPengeluaranById {

  override suspend fun invoke(id: UUID): DetailPengeluaran = withContext(ioDispather) {
    return@withContext pengeluaranRepository.findDetailById(id)
  }
}