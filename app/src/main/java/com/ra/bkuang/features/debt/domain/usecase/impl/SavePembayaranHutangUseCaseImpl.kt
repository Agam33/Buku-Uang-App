package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.debt.data.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutangUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePembayaranHutangUseCaseImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository,
  private val hutangRepository: HutangRepository,
  private val akunRepository: AkunRepository
): SavePembayaranHutangUseCase {
  override operator fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<Result<Boolean>> {
    return flow {
      val accountModel = akunRepository.findById(pembayaranHutangModel.idAkun)
      val debtModel = hutangRepository.findById(pembayaranHutangModel.idHutang)

      accountModel.total -= pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran += pembayaranHutangModel.jumlah

      pembayaranHutangRepository.save(pembayaranHutangModel).collect()

      emit(Result.Success(true))

      hutangRepository.update(debtModel).collect()
      akunRepository.update(accountModel).collect()
    }
  }
}