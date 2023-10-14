package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.SavePembayaranHutang
import com.ra.budgetplan.util.ResourceState
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
      val accountModel = akunRepository.findById(pembayaranHutangModel.idAkun).toModel()
      val debtModel = hutangRepository.findById(pembayaranHutangModel.idHutang).toModel()

      accountModel.total -= pembayaranHutangModel.jumlah
      debtModel.totalPengeluaran += pembayaranHutangModel.jumlah

      pembayaranHutangRepository.save(pembayaranHutangModel.toEntity())

      emit(ResourceState.SUCCESS)

      hutangRepository.update(debtModel.toEntity())
      akunRepository.update(accountModel.toEntity())
    }
  }
}