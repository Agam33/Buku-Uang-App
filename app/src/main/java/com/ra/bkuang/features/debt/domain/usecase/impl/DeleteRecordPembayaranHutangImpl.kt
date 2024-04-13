package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutang
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRecordPembayaranHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): DeleteRecordPembayaranHutang {
  override suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      val accountModel = akunRepository.findById(detailPembayaranHutangModel.akunModel.uuid).toModel()
      val debtModel = hutangRepository.findById(detailPembayaranHutangModel.hutangModel.uuid).toModel()

      accountModel.total += detailPembayaranHutangModel.pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran -= detailPembayaranHutangModel.pembayaranHutangModel.jumlah

      pembayaranHutangRepository.delete(detailPembayaranHutangModel.pembayaranHutangModel.toEntity())
      emit(ResourceState.SUCCESS)

      hutangRepository.update(debtModel.toEntity())
      akunRepository.update(accountModel.toEntity())
    }
  }
}