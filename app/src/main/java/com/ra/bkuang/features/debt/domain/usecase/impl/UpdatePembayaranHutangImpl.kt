package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutang
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePembayaranHutangImpl @Inject constructor(
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
      val accountModel = akunRepository.findById(oldModel.idAkun).toModel()
      val debtModel = hutangRepository.findById(oldModel.idHutang).toModel()

      pembayaranHutangRepository.update(newModel.toEntity())
      emit(ResourceState.SUCCESS)

      accountModel.total += oldModel.jumlah
      debtModel.totalPengeluaran -= oldModel.jumlah

      accountModel.total -= newModel.jumlah
      debtModel.totalPengeluaran += newModel.jumlah

      akunRepository.update(accountModel.toEntity())
      hutangRepository.update(debtModel.toEntity())
    }
  }
}