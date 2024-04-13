package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatan
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import javax.inject.Inject

class SavePendapatanImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
  private val akunRepository: AkunRepository
): SavePendapatan {
  override suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState {
    return try {
      val account = akunRepository.findById(pendapatanModel.idAkun)

      account.total += pendapatanModel.jumlah

      akunRepository.update(account)

      pendapatanRepository.save(pendapatanModel.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}