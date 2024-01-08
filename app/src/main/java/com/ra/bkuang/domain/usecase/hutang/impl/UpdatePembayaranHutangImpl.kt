package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.bkuang.domain.util.ResourceState
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