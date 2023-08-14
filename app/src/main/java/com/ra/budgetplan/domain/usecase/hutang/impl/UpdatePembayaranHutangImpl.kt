package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.budgetplan.util.StatusItem
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
  ): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)
      val accountModel = akunRepository.findById(oldModel.idAkun).toModel()
      val debtModel = hutangRepository.findById(oldModel.idHutang).toModel()

      pembayaranHutangRepository.update(newModel.toEntity())
      emit(StatusItem.SUCCESS)

      accountModel.total += oldModel.jumlah
      debtModel.totalPengeluaran -= oldModel.jumlah

      accountModel.total -= newModel.jumlah
      debtModel.totalPengeluaran += newModel.jumlah

      akunRepository.update(accountModel.toEntity())
      hutangRepository.update(debtModel.toEntity())
    }
  }
}