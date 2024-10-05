package com.ra.bkuang.features.debt.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.common.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.core.data.source.local.database.data.PembayaranHutangLocalDataSource
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.debt.data.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.data.model.PembayaranHutangModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class PembayaranHutangRepositoryImpl @Inject constructor(
    private val localDataSource: PembayaranHutangLocalDataSource,
    @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
): PembayaranHutangRepository {
  override fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?> {
    return localDataSource.getSizeListPembayaranHutangById(id)
  }

  override fun findAllRecordByHutangId(id: String): Flow<Result<List<DetailPembayaranHutangModel>>> {
    return flow {
      localDataSource.findAllRecordByHutangId(UUID.fromString(id)).collect {
        val data = mutableListOf<DetailPembayaranHutangModel>()
        for(pembayaranModel in it) {
          val model = DetailPembayaranHutangModel(
            pembayaranModel.pembayaranHutang.toModel(),
            pembayaranModel.akun.toModel(),
            pembayaranModel.hutangEntity.toModel()
          )
          data.add(model)
        }

        if(data.isEmpty()) {
          emit(Result.Error("List is empty"))
        }
        emit(Result.Success(data))
      }
    }
  }

  override fun save(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.save(pembayaranHutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun delete(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.delete(pembayaranHutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun update(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.update(pembayaranHutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }
}