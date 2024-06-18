package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.common.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.repository.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
  @IoDispatcherQualifier private val  ioDispatcher: CoroutineDispatcher,
  private val localDataSource: TransferLocalDataSource,
  private val akunLocalDataSource: AkunLocalDataSource
): TransferRepository {
  override suspend fun findById(uuid: UUID): TransferModel {
    return localDataSource.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailTransfer {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<Result<List<DetailTransfer>>> {
    return flow {
      val list = localDataSource.getTransferByDate(fromDate, toDate).first()

      if(list.isEmpty()) {
        emit(Result.Error("Empty list."))
        return@flow
      }

      emit(Result.Success(list))
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun save(transfer: TransferModel): Flow<Result<Boolean>> {
    return flow {
      try {
        val toAccount = akunLocalDataSource.findById(transfer.idToAkun).toModel()
        val fromAccount = akunLocalDataSource.findById(transfer.idFromAkun).toModel()

        toAccount.total += transfer.jumlah
        fromAccount.total -= transfer.jumlah

        localDataSource.save(transfer.toEntity())

        akunLocalDataSource.update(toAccount.toEntity())
        akunLocalDataSource.update(fromAccount.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun delete(uuid: UUID): Flow<Result<Boolean>> {
    return flow {
      try {
        val transfer = localDataSource.findById(uuid).toModel()

        localDataSource.delete(transfer.toEntity())

        val fromAccount = akunLocalDataSource.findById(transfer.idFromAkun).toModel()
        val toAccount = akunLocalDataSource.findById(transfer.idToAkun).toModel()

        fromAccount.total += transfer.jumlah
        toAccount.total -= transfer.jumlah

        akunLocalDataSource.update(fromAccount.toEntity())
        akunLocalDataSource.update(toAccount.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun update(newTransferModel: TransferModel, oldTransferModel: TransferModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.update(newTransferModel.toEntity())

        val newFromAccount = akunLocalDataSource.findById(newTransferModel.idFromAkun).toModel()
        val newToAccount = akunLocalDataSource.findById(newTransferModel.idToAkun).toModel()

        newFromAccount.total -= newTransferModel.jumlah
        newToAccount.total += newTransferModel.jumlah

        akunLocalDataSource.update(newFromAccount.toEntity())
        akunLocalDataSource.update(newToAccount.toEntity())

        val fromAccount = akunLocalDataSource.findById(oldTransferModel.idFromAkun).toModel()
        val toAccount = akunLocalDataSource.findById(oldTransferModel.idToAkun).toModel()

        fromAccount.total += oldTransferModel.jumlah
        toAccount.total -= oldTransferModel.jumlah

        akunLocalDataSource.update(fromAccount.toEntity())
        akunLocalDataSource.update(toAccount.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }
}