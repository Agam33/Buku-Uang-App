package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.DetailPembayaranHutangModel
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.budgetplan.util.Resource
import java.util.UUID
import javax.inject.Inject

class FindAllRecordPembayaranHutangImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): FindAllRecordPembayaranHutang {
  override suspend fun invoke(id: UUID): Resource<List<DetailPembayaranHutangModel>> {
    val data = mutableListOf<DetailPembayaranHutangModel>()

    for(pembayaranModel in pembayaranHutangRepository.findAllRecordByHutangId(id)) {
      val model = DetailPembayaranHutangModel(
        pembayaranModel.pembayaranHutang.toModel(),
        pembayaranModel.akun.toModel(),
        pembayaranModel.hutangEntity.toModel()
      )
      data.add(model)
    }

    if(data.isEmpty()) {
      return Resource.Empty("")
    }
    return Resource.Success(data)
  }
}