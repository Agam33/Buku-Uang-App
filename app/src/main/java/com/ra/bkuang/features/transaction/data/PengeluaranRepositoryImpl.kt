package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.budget.data.local.BudgetLocalDataSource
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  @IoDispatcherQualifier private val  ioDispatcher: CoroutineDispatcher,
  private val accountLocalData: AkunLocalDataSource,
  private val budgetLocalData: BudgetLocalDataSource,
  private val expenseLocalData: PengeluaranLocalDataSource
): PengeluaranRepository {
  override suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    idKategori: UUID
  ): Long? {
    return expenseLocalData.getTotalPengeluaranByDateAndKategory(fromDate, toDate, idKategori)
  }

  override suspend fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return expenseLocalData.getTotalPengeluaran(fromDate, toDate)
  }

  override suspend fun findById(uuid: UUID): PengeluaranModel {
    return expenseLocalData.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailPengeluaran {
    return expenseLocalData.findDetailById(uuid)
  }

  override fun getTotalPengeluaranByDateWithFlow(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long> {
    return expenseLocalData.getTotalPengeluaranByDate(fromDate, toDate).map { it ?: 0 }
  }

  override fun getTotalPengeluaranWithFlow(): Flow<Long?> {
    return expenseLocalData.getTotalPengeluaran()
  }

  override fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>> {
    return expenseLocalData.getMonthlyPengeluaran(startOfDay, endOfDay)
  }

  override fun getListDetailPengeluaranByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<Result<List<DetailPengeluaran>>> {
    return flow {
      val it = expenseLocalData.getPengeluaranByDate(fromDate, toDate).first()

      if(it.isEmpty()) {
        emit(Result.Error("List is empty"))
        return@flow
      }

      emit(Result.Success(it))
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun save(expenseModel: PengeluaranModel): Flow<Result<Boolean>> {
    return flow {
      try {
        val account = accountLocalData.findById(expenseModel.idAkun).toModel()

        expenseLocalData.save(expenseModel.toEntity())

        account.total -= expenseModel.jumlah

        val fromDate = expenseModel.updatedAt
          .toLocalDate().withDayOfMonth(1)
        val toDate = expenseModel.updatedAt
          .toLocalDate().withDayOfMonth(expenseModel.updatedAt.toLocalDate().dayOfMonth)
        val katId = expenseModel.idKategori

        val isBudgetExist = budgetLocalData.isExistByDateAndKategoriId(
          fromDate,
          toDate,
          katId
        )

        if(isBudgetExist) {
          val budgetModel = budgetLocalData.findBudgetByDateAndKategoriId(
            fromDate,
            toDate,
            katId
          ).toModel()

          budgetModel.pengeluaran += expenseModel.jumlah

          budgetLocalData.update(budgetModel.toEntity())
        }

        accountLocalData.update(account.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun delete(uuid: UUID): Flow<Result<Boolean>> {
    return flow {
      try {
        val pengeluaran = expenseLocalData.findById(uuid).toModel()

        expenseLocalData.delete(pengeluaran.toEntity())

        val account = accountLocalData.findById(pengeluaran.idAkun).toModel()
        account.total += pengeluaran.jumlah

        val fromDate = pengeluaran.updatedAt.toLocalDate().withDayOfMonth(1)
        val toDate = pengeluaran.updatedAt.toLocalDate()
          .withDayOfMonth(pengeluaran.updatedAt.toLocalDate().dayOfMonth)
        val katId = pengeluaran.idKategori

        val isBudgetExist = budgetLocalData.isExistByDateAndKategoriId(
          fromDate,
          toDate,
          katId
        )

        if (isBudgetExist) {
          val budgetModel = budgetLocalData.findBudgetByDateAndKategoriId(
            fromDate,
            toDate,
            katId
          ).toModel()

          budgetModel.pengeluaran -= pengeluaran.jumlah

          budgetLocalData.update(budgetModel.toEntity())
        }

        accountLocalData.update(account.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun update(newExpenseModel: PengeluaranModel, oldExpenseModel: PengeluaranModel): Flow<Result<Boolean>> {
    return flow {
      try {
        expenseLocalData.update(newExpenseModel.toEntity())

        val newAccount = accountLocalData.findById(newExpenseModel.idAkun).toModel()

        newAccount.total -= newExpenseModel.jumlah

        accountLocalData.update(newAccount.toEntity())

        val oldAccount = accountLocalData.findById(oldExpenseModel.idAkun).toModel()

        oldAccount.total += oldExpenseModel.jumlah

        val fromDate = oldExpenseModel.updatedAt.toLocalDate().withDayOfMonth(1)
        val toDate = oldExpenseModel.updatedAt.toLocalDate().withDayOfMonth(oldExpenseModel.updatedAt.toLocalDate().dayOfMonth)
        val katId = oldExpenseModel.idKategori

        val isBudgetExist = budgetLocalData.isExistByDateAndKategoriId(
          fromDate,
          toDate,
          katId
        )

        if(isBudgetExist) {
          val budgetModel = budgetLocalData.findBudgetByDateAndKategoriId(
            fromDate,
            toDate,
            katId
          ).toModel()

          budgetModel.pengeluaran -= oldExpenseModel.jumlah
          budgetModel.pengeluaran += newExpenseModel.jumlah

          budgetLocalData.update(budgetModel.toEntity())
        }

        accountLocalData.update(oldAccount.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }
}