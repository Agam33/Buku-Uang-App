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
import java.util.UUID

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

  @Query("SELECT SUM(jumlah) FROM pengeluaran_tbl " +
          "WHERE " +
          "pengeluaran_tbl.updated_at BETWEEN :fromDate AND :toDate")
  suspend fun getTotalPengeluaran(fromDate: LocalDateTime, toDate: LocalDateTime): Long?

  @Query("SELECT SUM(jumlah) FROM pengeluaran_tbl")
  fun getTotalPengeluaran(): Flow<Long?>

  @Query("SELECT * FROM pengeluaran_tbl AS p WHERE p.uuid = :uuid")
  suspend fun findDetailPengeluaranById(uuid: UUID): DetailPengeluaran

  @Query("SELECT * FROM pengeluaran_tbl AS p WHERE p.uuid = :uuid")
  suspend fun findById(uuid: UUID): PengeluaranEntity

  @Insert
  suspend fun save(pengeluaran: PengeluaranEntity)

  @Delete
  suspend fun delete(pengeluaran: PengeluaranEntity)

  @Update
  suspend fun update(pengeluaran: PengeluaranEntity)

  @Query("SELECT SUM(jumlah) FROM pengeluaran_tbl AS p WHERE p.id_kategori =:idKategori " +
          "AND p.updated_at BETWEEN :fromDate AND :toDate")
  suspend fun getTotalPengeluaranByDateAndKategori(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    idKategori: UUID
  ): Long?
}