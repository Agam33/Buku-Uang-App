package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.util.ResourceState
import javax.inject.Inject

class SavePendapatanImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
  private val akunRepository: AkunRepository
): SavePendapatan {
  override suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState {
    return try {
      val account = akunRepository.findById(pendapatanModel.idAkun).toModel()

      account.total += pendapatanModel.jumlah

      akunRepository.update(account.toEntity())

      pendapatanRepository.save(pendapatanModel.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}