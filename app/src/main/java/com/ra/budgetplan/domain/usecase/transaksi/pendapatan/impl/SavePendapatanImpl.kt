package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SavePendapatanImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
  private val akunRepository: AkunRepository
): SavePendapatan {
  override suspend fun invoke(pendapatanModel: PendapatanModel) {
    val account = akunRepository.findById(pendapatanModel.idAkun).first().toModel()

    account.total += pendapatanModel.jumlah

    akunRepository.update(account.toEntity())

    pendapatanRepository.save(pendapatanModel.toEntity())
  }
}