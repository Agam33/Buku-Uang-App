package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.BudgetEntity

@Dao
interface BudgetDao {

  @Insert
  suspend fun save(budget: BudgetEntity)

  @Delete
  suspend fun delete(budget: BudgetEntity)

  @Update
  suspend fun update(budget: BudgetEntity)
}