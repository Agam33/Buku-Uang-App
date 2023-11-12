package com.ra.bkuang.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ra.bkuang.data.local.entity.DetailPendapatan
import com.ra.bkuang.data.local.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

@Dao
interface PendapatanDao {

  @Transaction
  @Query("SELECT * FROM pendapatan_tbl " +
          "WHERE " +
          "pendapatan_tbl.updated_at BETWEEN :fromDate AND :toDate " +
          "ORDER BY pendapatan_tbl.updated_at DESC")
  suspend fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>

  @Query("SELECT SUM(jumlah) FROM pendapatan_tbl " +
          "WHERE " +
          "pendapatan_tbl.updated_at BETWEEN :fromDate AND :toDate")
  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>

  @Query("SELECT SUM(jumlah) FROM pendapatan_tbl " +
          "WHERE " +
          "pendapatan_tbl.updated_at BETWEEN :fromDate AND :toDate")
  suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long?

  @Query("SELECT SUM(jumlah) FROM pendapatan_tbl")
  fun getTotalPendapatan(): Flow<Long?>
  @Query("SELECT * FROM pendapatan_tbl AS pdt WHERE pdt.uuid = :uuid")
  suspend fun findDetailPendapatanById(uuid: UUID): DetailPendapatan

  @Query("SELECT * FROM pendapatan_tbl AS pdt WHERE pdt.uuid = :uuid")
  suspend fun findById(uuid: UUID): PendapatanEntity

  @Insert
  suspend fun save(pendapatan: PendapatanEntity)

  @Delete
  suspend fun delete(pendapatan: PendapatanEntity)

  @Update
  suspend fun update(pendapatan: PendapatanEntity)
}