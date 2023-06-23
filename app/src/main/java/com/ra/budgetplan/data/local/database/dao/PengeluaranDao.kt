package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface PengeluaranDao {
  @Transaction
  @Query("SELECT * FROM pengeluaran_tbl " +
          "WHERE " +
          "pengeluaran_tbl.updated_at BETWEEN :fromDate AND :toDate " +
          "ORDER BY pengeluaran_tbl.updated_at DESC")
  suspend fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>

  @Transaction
  @Query("SELECT * FROM pengeluaran_tbl " +
          "WHERE " +
          "pengeluaran_tbl.updated_at BETWEEN :startOfDay AND :endOfDay " +
          "ORDER BY pengeluaran_tbl.updated_at DESC")
  fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>>

  @Query("SELECT SUM(jumlah) FROM pengeluaran_tbl " +
          "WHERE " +
          "pengeluaran_tbl.updated_at BETWEEN :fromDate AND :toDate")
  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>

  @Query("SELECT SUM(jumlah) FROM pengeluaran_tbl")
  fun getTotalPengeluaran(): Flow<Long?>

  @Insert
  suspend fun save(pengeluaran: PengeluaranEntity)

  @Delete
  suspend fun delete(pengeluaran: PengeluaranEntity)

  @Update
  suspend fun update(pengeluaran: PengeluaranEntity)
}