package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.PendapatanModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.bkuang.util.ResourceState
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