package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface PendapatanDao {

  @Transaction
  @Query("SELECT * FROM pendapatan_tbl " +
          "WHERE " +
          "pendapatan_tbl.updated_at BETWEEN :fromDate AND :toDate " +
          "ORDER BY pendapatan_tbl.updated_at DESC")
  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailPendapatan>>

  @Insert
  suspend fun save(pendapatan: PendapatanEntity)

  @Delete
  suspend fun delete(pendapatan: PendapatanEntity)

  @Update
  suspend fun update(pendapatan: PendapatanEntity)
}