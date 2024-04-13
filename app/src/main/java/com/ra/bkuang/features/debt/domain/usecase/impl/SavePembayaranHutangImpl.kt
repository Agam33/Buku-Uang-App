package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutang
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePembayaranHutangImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository,
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository
): SavePembayaranHutang {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      val accountModel = akunRepository.findById(pembayaranHutangModel.idAkun)
      val debtModel = hutangRepository.findById(pembayaranHutangModel.idHutang).toModel()

      accountModel.total -= pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran += pembayaranHutangModel.jumlah

      pembayaranHutangRepository.save(pembayaranHutangModel.toEntity())

      emit(ResourceState.SUCCESS)

      hutangRepository.update(debtModel.toEntity())
      akunRepository.update(accountModel)
    }
  }
}