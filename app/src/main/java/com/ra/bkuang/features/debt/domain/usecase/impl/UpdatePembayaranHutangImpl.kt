package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdatePembayaranHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val akunRepository: AkunRepository,
  private val hutangRepository: HutangRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): UpdatePembayaranHutang {

  override suspend fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      val accountModel = akunRepository.findById(oldModel.idAkun)
      val debtModel = hutangRepository.findById(oldModel.idHutang)

      pembayaranHutangRepository.update(newModel)
      emit(ResourceState.SUCCESS)

      accountModel.total += oldModel.jumlah
      debtModel.totalPengeluaran -= oldModel.jumlah

      accountModel.total -= newModel.jumlah
      debtModel.totalPengeluaran += newModel.jumlah

      akunRepository.update(accountModel)
      hutangRepository.update(debtModel)
    }
      .flowOn(ioDispatcher)
  }
}