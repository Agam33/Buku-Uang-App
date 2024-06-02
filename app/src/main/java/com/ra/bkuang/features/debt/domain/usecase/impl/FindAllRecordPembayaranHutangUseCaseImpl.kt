package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutangUseCase
import java.util.UUID
import javax.inject.Inject

class FindAllRecordPembayaranHutangUseCaseImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): FindAllRecordPembayaranHutangUseCase {
  override suspend fun invoke(id: String): ResultState<List<DetailPembayaranHutangModel>> {
    val data = mutableListOf<DetailPembayaranHutangModel>()

    for(pembayaranModel in pembayaranHutangRepository.findAllRecordByHutangId(UUID.fromString(id))) {
      val model = DetailPembayaranHutangModel(
        pembayaranModel.pembayaranHutang.toModel(),
        pembayaranModel.akun.toModel(),
        pembayaranModel.hutangEntity.toModel()
      )
      data.add(model)
    }

    if(data.isEmpty()) {
      return ResultState.Empty
    }
    return ResultState.Success(data)
  }
}