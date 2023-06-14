package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransferDao {

  @Transaction
  @Query("SELECT * FROM transfer_tbl " +
          "WHERE " +
          "transfer_tbl.updated_at BETWEEN :fromDate AND :toDate " +
          "ORDER BY transfer_tbl.updated_at DESC")
  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailTransfer>>

  @Insert
  suspend fun save(transferEntity: TransferEntity)

  @Delete
  suspend fun delete(transferEntity: TransferEntity)

  @Update
  suspend fun update(transferEntity: TransferEntity)
}