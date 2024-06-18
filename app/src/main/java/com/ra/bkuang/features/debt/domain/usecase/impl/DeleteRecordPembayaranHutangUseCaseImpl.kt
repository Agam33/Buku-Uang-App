package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutangUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRecordPembayaranHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): DeleteRecordPembayaranHutangUseCase {
  override fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<Result<Boolean>> {
    return flow {

      val accountModel = akunRepository.findById(detailPembayaranHutangModel.akunModel.uuid)
      val debtModel = hutangRepository.findById(detailPembayaranHutangModel.hutangModel.uuid)

      accountModel.total += detailPembayaranHutangModel.pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran -= detailPembayaranHutangModel.pembayaranHutangModel.jumlah

      pembayaranHutangRepository.delete(detailPembayaranHutangModel.pembayaranHutangModel).collect()

      emit(Result.Success(true))

      hutangRepository.update(debtModel).collect()
      akunRepository.update(accountModel).collect()
    }
  }
}