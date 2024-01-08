package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.bkuang.domain.util.ResourceState
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