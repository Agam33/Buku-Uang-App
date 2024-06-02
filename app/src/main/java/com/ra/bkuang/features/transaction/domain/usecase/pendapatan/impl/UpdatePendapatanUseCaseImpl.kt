package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatanUseCase
import javax.inject.Inject

class UpdatePendapatanUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): UpdatePendapatanUseCase {
  override suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState {
    return try {
      repository.update(newPendapatanModel)

      val newAccount = accountRepository.findById(newPendapatanModel.idAkun)

      newAccount.total += newPendapatanModel.jumlah

      accountRepository.update(newAccount)

      val oldAccount = accountRepository.findById(oldPendapatanModel.idAkun)

      oldAccount.total -= oldPendapatanModel.jumlah

      accountRepository.update(oldAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}