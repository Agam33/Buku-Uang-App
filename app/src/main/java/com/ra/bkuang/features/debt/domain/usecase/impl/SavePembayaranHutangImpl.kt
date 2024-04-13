package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SavePembayaranHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val pembayaranHutangRepository: PembayaranHutangRepository,
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository
): SavePembayaranHutang {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      val accountModel = akunRepository.findById(pembayaranHutangModel.idAkun)
      val debtModel = hutangRepository.findById(pembayaranHutangModel.idHutang)

      accountModel.total -= pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran += pembayaranHutangModel.jumlah

      pembayaranHutangRepository.save(pembayaranHutangModel)

      emit(ResourceState.SUCCESS)

      hutangRepository.update(debtModel)
      akunRepository.update(accountModel)
    }.flowOn(ioDispatcher)
  }
}