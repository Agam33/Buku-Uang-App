package com.ra.bkuang.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ra.bkuang.domain.entity.BudgetEntity
import com.ra.bkuang.domain.entity.DetailBudget
import java.time.LocalDate
import java.util.UUID

@Dao
interface BudgetDao {

  @Insert
  suspend fun save(budget: BudgetEntity)

  @Delete
  suspend fun delete(budget: BudgetEntity)

  @Update
  suspend fun update(budget: BudgetEntity)

  @Query("SELECT * FROM budget_tbl AS budget WHERE budget.uuid = :id")
  suspend fun findById(id: UUID): BudgetEntity

  @Query("SELECT * FROM budget_tbl AS budget WHERE budget.id_kategori = :id " +
          "AND budget.bulan_tahun BETWEEN :fromDate AND :toDate")
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetEntity

  @Query("SELECT EXISTS(SELECT * FROM budget_tbl AS budget WHERE budget.id_kategori = :id " +
          "AND budget.bulan_tahun BETWEEN :fromDate AND :toDate)")
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean

  @Query("SELECT * FROM budget_tbl AS budget WHERE budget.bulan_tahun BETWEEN :fromDate AND :toDate")
  suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
}