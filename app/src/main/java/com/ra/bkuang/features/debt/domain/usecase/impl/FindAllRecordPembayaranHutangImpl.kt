package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutang
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.debt.data.mapper.toModel
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