package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreatePembayaranHutangUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreatePembayaranHutangUseCaseImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): CreatePembayaranHutangUseCase {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel) = withContext(ioDispatcher) {
    return@withContext pembayaranHutangRepository.save(pembayaranHutangModel)
  }
}