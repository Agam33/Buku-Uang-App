package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.bkuang.util.Resource
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