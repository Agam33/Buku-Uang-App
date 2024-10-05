package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.debt.data.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllRecordPembayaranHutangUseCaseImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): FindAllRecordPembayaranHutangUseCase {
  override operator fun invoke(id: String): Flow<Result<List<DetailPembayaranHutangModel>>> {
    return pembayaranHutangRepository.findAllRecordByHutangId(id)
  }
}