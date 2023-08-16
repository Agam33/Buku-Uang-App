package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.DetailPembayaranHutangModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRecordPembayaranHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): DeleteRecordPembayaranHutang {
  override suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)

      val accountModel = akunRepository.findById(detailPembayaranHutangModel.akunModel.uuid).toModel()
      val debtModel = hutangRepository.findById(detailPembayaranHutangModel.hutangModel.uuid).toModel()

      accountModel.total += detailPembayaranHutangModel.pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran -= detailPembayaranHutangModel.pembayaranHutangModel.jumlah

      pembayaranHutangRepository.delete(detailPembayaranHutangModel.pembayaranHutangModel.toEntity())
      emit(StatusItem.SUCCESS)

      hutangRepository.update(debtModel.toEntity())
      akunRepository.update(accountModel.toEntity())
    }
  }
}