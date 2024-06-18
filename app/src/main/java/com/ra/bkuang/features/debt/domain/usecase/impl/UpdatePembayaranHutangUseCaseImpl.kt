package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutangUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePembayaranHutangUseCaseImpl @Inject constructor(
  private val akunRepository: AkunRepository,
  private val hutangRepository: HutangRepository,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): UpdatePembayaranHutangUseCase {

  override fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<Result<Boolean>> {
    return flow {
      try {
        val accountModel = akunRepository.findById(oldModel.idAkun)
        val debtModel = hutangRepository.findById(oldModel.idHutang)

        pembayaranHutangRepository.update(newModel).collect()

        accountModel.total += oldModel.jumlah
        debtModel.totalPengeluaran -= oldModel.jumlah

        accountModel.total -= newModel.jumlah
        debtModel.totalPengeluaran += newModel.jumlah

        akunRepository.update(accountModel).collect()
        hutangRepository.update(debtModel).collect()

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
  }
}