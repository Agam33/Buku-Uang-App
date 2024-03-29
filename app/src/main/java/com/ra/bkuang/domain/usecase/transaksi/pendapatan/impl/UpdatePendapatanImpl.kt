package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.PendapatanModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.UpdatePendapatan
import com.ra.bkuang.util.ResourceState
import javax.inject.Inject

class UpdatePendapatanImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): UpdatePendapatan {
  override suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState {
    return try {
      repository.update(newPendapatanModel.toEntity())

      val newAccount = accountRepository.findById(newPendapatanModel.idAkun).toModel()

      newAccount.total += newPendapatanModel.jumlah

      accountRepository.update(newAccount.toEntity())

      val oldAccount = accountRepository.findById(oldPendapatanModel.idAkun).toModel()

      oldAccount.total -= oldPendapatanModel.jumlah

      accountRepository.update(oldAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}