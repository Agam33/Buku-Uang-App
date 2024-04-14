package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteRecordPembayaranHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): DeleteRecordPembayaranHutang {
  override suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      val accountModel = akunRepository.findById(detailPembayaranHutangModel.akunModel.uuid)
      val debtModel = hutangRepository.findById(detailPembayaranHutangModel.hutangModel.uuid)

      accountModel.total += detailPembayaranHutangModel.pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran -= detailPembayaranHutangModel.pembayaranHutangModel.jumlah

      pembayaranHutangRepository.delete(detailPembayaranHutangModel.pembayaranHutangModel)
      emit(ResourceState.SUCCESS)

      hutangRepository.update(debtModel)
      akunRepository.update(accountModel)
    }
      .flowOn(ioDispatcher)
  }
}