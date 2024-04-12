package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.bkuang.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindAllRecordPembayaranHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val pembayaranHutangRepository: PembayaranHutangRepository
): FindAllRecordPembayaranHutang {
  override suspend fun invoke(id: String): Resource<List<DetailPembayaranHutangModel>> = withContext(ioDispatcher) {
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
      return@withContext Resource.Empty("")
    }
    return@withContext Resource.Success(data)
  }
}