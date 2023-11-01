package com.ra.bkuang.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.data.entity.TransferEntity
import java.time.LocalDateTime
import java.util.UUID

@Dao
interface TransferDao {

  @Transaction
  @Query("SELECT * FROM transfer_tbl " +
          "WHERE " +
          "transfer_tbl.updated_at BETWEEN :fromDate AND :toDate " +
          "ORDER BY transfer_tbl.updated_at DESC")
  suspend fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>

  @Query("SELECT * FROM transfer_tbl AS t WHERE t.uuid = :uuid")
  suspend fun findDetailTransferById(uuid: UUID): DetailTransfer

  @Query("SELECT * FROM transfer_tbl AS t WHERE t.uuid = :uuid")
  suspend fun findById(uuid: UUID): TransferEntity

  @Insert
  suspend fun save(transferEntity: TransferEntity)

  @Delete
  suspend fun delete(transferEntity: TransferEntity)

  @Update
  suspend fun update(transferEntity: TransferEntity)
}